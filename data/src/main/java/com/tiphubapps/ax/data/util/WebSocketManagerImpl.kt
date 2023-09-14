package com.tiphubapps.ax.data.util

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.tiphubapps.ax.domain.model.User
import com.tiphubapps.ax.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject
import java.io.IOException
import java.util.Calendar
import java.util.Date
import java.util.concurrent.Executors
import javax.inject.Inject

class WebSocketManagerImpl @Inject constructor(private val authRepository: AuthRepository,
                                               private val client: OkHttpClient,
                                               private val coroutineContextProvider: CoroutineContextProvider
                                               ): WebSocketManager {

    private val _state = MutableStateFlow(WebSocketState())
    val state : StateFlow<WebSocketState> = _state

    override val isConnected = _state.map{ it.connected }

    var ws: WebSocket? = null
    //private val client = okHttpClient
    val WEBSOCKET_URL = "ws://3.239.168.240"

    lateinit var request: Request
    private var listener: WebSocketListener? = null

    //TODO: Implement threaded execution in MainActivity? or ViewModel?
    // EXTEND AuthedViewModel with WebSocketViewModel (or implement TipRepository?)
    // do something from WebSocketViewModel/Manager to user feedback interactions (can be lossy)
    // still would do DB updates in background here though
    var thread = Executors.newSingleThreadExecutor()

    override suspend fun connect(): Boolean {
        return withContext(coroutineContextProvider.io) {

            authRepository.getToken()?.let {

                val request: Request = Request.Builder()
                    .url(WEBSOCKET_URL)
                    .addHeader("Authorization", "Bearer ${it}")
                    .build()

                ws = client.newWebSocket(request, createWebSocketListener())

                onEvent(WebSocketEvent.UpdateConnected(true))
                return@withContext true
            }
            return@withContext false
        }
    }

    override suspend fun disconnect() {
        withContext(coroutineContextProvider.io) {
            //TODO: Check for other garbage
            try {
                ws?.close(1000, "User disconnected")
                onEvent(WebSocketEvent.UpdateConnected(false))
            } catch (e: IOException) {
                // Handle disconnection error
                // Implement reconnection logic here
            }
            //ws = null
        }
    }

    override fun setListener(newListener: WebSocketListener) {
        listener = newListener
    }


    private fun createWebSocketListener(): WebSocketListener {
        return object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                val currentTime: Date = Calendar.getInstance().time
                Log.d("TIME123", "From SOCKET:" + text + currentTime)

                handleReceivedText(text)

                listener?.onMessage(webSocket, text)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                //handleFailure(webSocket, t, response)
                // Handle connection failure and notify the listener.
                listener?.onFailure(webSocket, t, response)
            }
        }
    }




private fun handleReceivedText(text: String) {
    // 1. Parse the incoming text as a JSON object
    // Replace this part with your JSON parsing library if you use a different one
    val jsonObject = JsonParser.parseString(text).asJsonObject

    // 2. Extract the 'action' property from the JSON object
    val action = jsonObject["action"]?.asString

    //sessionid
    //val action = jsonObject["action"]?.asString
    //previousId
    //val action = jsonObject["action"]?.asString
    //amount
    //val action = jsonObject["action"]?.asString

    // 3. Use the extracted 'action' in the when statement
    when (action) {
        // Handle actions here
        "RECEIVER_TIP" -> {
            // Handle the RECEIVER_TIP action
            println("Socket Action: RECEIVER_TIP")

            jsonObject["amount"]?.let{
                println("Received tip amount: $it")
                //add tip event, amount
            }

        }
        "RECEIVE_MESSAGE" -> {
            // Handle the RECEIVER_MESSAGE action
            println("Socket Action: RECEIVER_MESSAGE")

            jsonObject["amount"]?.let{
                println("Received message amount: $it")
            }

        }
        "RECEIVER_TIP_RAIN" -> {
            // Handle the RECEIVER_TIP_RAIN action
            println("Socket Action: RECEIVER_TIP_RAIN")

            jsonObject["amount"]?.let{
                println("Received rain amount: $it")
                //add tip rain event, amount
            }
        }
        "THANK_YOU" -> {
            // Handle the THANK_YOU action
            println("Socket Action: THANK_YOU")
        }
        "ACK" -> {
            // Handle the ACK action
            println("Socket Action: ACK")

            jsonObject["hash"]?.let{
                onEvent(WebSocketEvent.UpdateSelectedUserSession(it.asString))
            }

            onEvent(WebSocketEvent.UpdateTipState(TipState.Success(jsonObject)))
        }
        "REFRESH" -> {
            // Handle the REFRESH action
            println("Socket Action: REFRESH")

            jsonObject["previous"]?.let{
                onEvent(WebSocketEvent.UpdateSelectedUserSession(it.asString))
            }

            onEvent(WebSocketEvent.UpdateTipState(TipState.Success(jsonObject)))

        }
        "RECEIVER_TIP_BAG" -> {
            // Handle the RECEIVER_TIP_BAG action
            println("Socket Action: RECEIVER_TIP_BAG")

            jsonObject["amount"]?.let{
                println("Received bag amount: $it")
                //add tip event, amount
            }
        }
        else -> {
            println("Unknown action")
            onEvent(WebSocketEvent.UpdateTipState(TipState.Default))
        }
    }
}

    override fun sendMessage(message: String){
    //TODO:
    // message string will turn to ACTION VALUE
    // selection for  amount / tip type
        when(_state.value.tipState){
            is TipState.Loading -> { /*do not send message til ack response*/ }
            else -> {
                Log.d("TIME123", "SOCKET CONNECTED?")

                val jsonOb = JSONObject().apply {
                    put("action", "TIP_USER");
                    put("sender", _state.value.currentUserId);
                    put("receiver", _state.value.selectedUserId);
                    put("value", "1")
                    put("previous", _state.value.selectedUserSession)
                    put("sessionId", _state.value.selectedUserSessionId)
                }

                Log.d("TIME123", "WEBSOCKET DATA:" + jsonOb.toString())

                //TODO:
                // ...check this over....

                ws?.let {
                    val send = it.send(jsonOb.toString());
                    println("TIME123 Sending message... SENT?" + send)

                    if(send){
                        onEvent(WebSocketEvent.UpdateTipState(TipState.Loading))
                    }
                }
            }
        }

    }

    //UPDATE STATE EVENTS//

    fun onEvent(event: WebSocketEvent) {
        when(event){
            //is WebSocketEvent.UpdateToken -> { _state.value = _state.value.copy(token = event.token) }
            is WebSocketEvent.UpdateConnected -> { _state.value = _state.value.copy(connected = event.connected) }
            is WebSocketEvent.UpdateTipState -> { _state.value = _state.value.copy(tipState = event.tipState) }
            is WebSocketEvent.UpdateCurrentUser -> { _state.value = _state.value.copy(currentUserId = event.id) }
            is WebSocketEvent.UpdateSelectedUser -> { _state.value = _state.value.copy(selectedUserId = event.id) }
            is WebSocketEvent.UpdateSelectedUserSession -> { _state.value = _state.value.copy(selectedUserSession = event.id) }
            is WebSocketEvent.UpdateSelectedUserSessionId -> { _state.value = _state.value.copy(selectedUserSessionId = event.id) }
            else -> { /* add tip message, consume, throttle, expire/garbage collect unread messages */}
        }
    }

    ///////////////////////

    override fun clearEvents(){
       // _uiStateEvent.value = EventUiState.DEFAULT;
       // _tipState.value = TipState.Default

        onEvent(WebSocketEvent.UpdateTipState(TipState.Default))
        //clear/remove list of event messages...
    }

    /////STATE EVENTS//////
    sealed class WebSocketEvent{
        /*object OnConnect: WebSocketEvent()
        object OnDisconnect: WebSocketEvent()
        object OnSetListener: WebSocketEvent()
        object OnMessageReceived: WebSocketEvent()*/
        //data class UpdateToken(val token: String): WebSocketEvent()
        data class UpdateConnected(val connected: Boolean): WebSocketEvent()
        data class UpdateTipState(val tipState: TipState): WebSocketEvent()
        data class UpdateCurrentUser(val id: String): WebSocketEvent()
        data class UpdateSelectedUser(val id: String): WebSocketEvent()
        data class UpdateSelectedUserSession(val id: String): WebSocketEvent()
        data class UpdateSelectedUserSessionId(val id: String): WebSocketEvent()


    }

    /////////////////

    /////STATES//////

    //TODO:
    // is this neccessary?> in memory cache...
    // SHould hold tokens, sessionIds, and cachedMessages, ack status
    data class WebSocketState(
        val connected: Boolean = false,
        //val token: String = "",
        val tipState: TipState = TipState.Default,
        val currentUserId: String = "",
        val selectedUserId: String = "",
        val selectedUserSession: String = "",
        val selectedUserSessionId: String = "",
        //messages
        //ack status? = tipState
    )

    sealed class TipState {
            object Default: TipState()
            data class Success(val message: JsonObject): TipState()
            object Loading: TipState()
            data class Error(val exception: Throwable): TipState()
    }

}