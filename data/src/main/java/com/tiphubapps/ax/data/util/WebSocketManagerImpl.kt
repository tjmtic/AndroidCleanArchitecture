package com.tiphubapps.ax.data.util

import android.util.Log
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

class WebSocketManagerImpl @Inject constructor(authRepository: AuthRepository,
                                               okHttpClient: OkHttpClient,
                                               private val coroutineContextProvider: CoroutineContextProvider
                                               ): WebSocketManager {

    //TODO:
    // Should come from authRepository? Implicit from LoginStatus
    private val _currentToken = MutableStateFlow<String>("")
    val token : StateFlow<String> = _currentToken
    private val _currentUserId = MutableStateFlow<String>("")
    val currentUserId : StateFlow<String> = _currentUserId

    //This still set separately?
    private val _selectedUserId = MutableStateFlow<String>("")
    val selectedUserId : StateFlow<String> = _selectedUserId

    private val _selectedUserSession = MutableStateFlow<String>("")
    val selectedUserSession : StateFlow<String> = _selectedUserSession

    private val _selectedUserSessionId = MutableStateFlow<String>("")
    val selectedUserSessionId : StateFlow<String> = _selectedUserSessionId


    //UI STATES
    private val _tipState = MutableStateFlow<TipState>(TipState.Default)
    val tipSate: StateFlow<TipState> = _tipState



    var ws: WebSocket? = null
    private val client = okHttpClient
    val WEBSOCKET_URL = "ws://3.239.168.240"

    lateinit var request: Request
    var listener: WebSocketListener? = null

    //TODO: Implement threaded execution in MainActivity?
    var thread = Executors.newSingleThreadExecutor()

    init {
        //Implicitly update from FLOW
        //TODO: Remove NULLABLE value
        authRepository.getToken()?.let{
            _currentToken.value = it
        }
    }

    override suspend fun connect(){
        withContext(coroutineContextProvider.io) {
            val request: Request = Request.Builder()
                .url(WEBSOCKET_URL)
                .addHeader("Authorization", "Bearer ${token.value}")
                .build()
            //viewModelScope.launch(thread.asCoroutineDispatcher() + coroutineExceptionHandler, CoroutineStart.DEFAULT) {
            ws = client.newWebSocket(request, createWebSocketListener())
            //}
        }
    }

    override suspend fun disconnect() {
        withContext(coroutineContextProvider.io) {
            //TODO: Check for other garbage
            try {
                ws?.close(1000, "User disconnected")
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
           // _uiStateEvent.value = EventUiState.TIP;
            jsonObject["amount"]?.let{
                println("Received tip amount: $it")
            }

        }
        "RECEIVE_MESSAGE" -> {
            // Handle the RECEIVER_MESSAGE action
            println("Socket Action: RECEIVER_MESSAGE")
            //_uiStateEvent.value = EventUiState.TIP;
            jsonObject["amount"]?.let{
                println("Received message amount: $it")
            }

        }
        "RECEIVER_TIP_RAIN" -> {
            // Handle the RECEIVER_TIP_RAIN action
            println("Socket Action: RECEIVER_TIP_RAIN")
            //_uiStateEvent.value = EventUiState.RAIN;
            jsonObject["amount"]?.let{
                println("Received rain amount: $it")
            }
        }
        "THANK_YOU" -> {
            // Handle the THANK_YOU action
            println("Socket Action: THANK_YOU")
            //_uiStateEvent.value = EventUiState.DEFAULT;
        }
        "ACK" -> {
            // Handle the ACK action
            println("Socket Action: ACK")
            //_uiStateEvent.value = EventUiState.DEFAULT;
            jsonObject["hash"]?.let{
                _selectedUserSession.value = it.asString
            }
            //TODO:
            // STATUS = NOTLOADING
            _tipState.value = TipState.Success(jsonObject)
        }
        "REFRESH" -> {
            // Handle the REFRESH action
            println("Socket Action: REFRESH")
            //_uiStateEvent.value = EventUiState.DEFAULT;
            jsonObject["previous"]?.let{
                _selectedUserSession.value = it.asString
            }
            //TODO:
            // STATUS = NOTLOADING
            _tipState.value = TipState.Success(jsonObject)

        }
        "RECEIVER_TIP_BAG" -> {
            // Handle the RECEIVER_TIP_BAG action
            println("Socket Action: RECEIVER_TIP_BAG")
           // _uiStateEvent.value = EventUiState.TIP;
            jsonObject["amount"]?.let{
                println("Received bag amount: $it")
            }
        }
        else -> {
            println("Unknown action")
           // _uiStateEvent.value = EventUiState.DEFAULT;
            //TODO:
            // STATUS = NOTLOADING??
            _tipState.value = TipState.Default
        }
    }
}

    override fun sendMessage(message: String){
    //TODO:
    // message string will turn to ACTION VALUE
    // selection for  amount / tip type
        when(_tipState.value){
            is TipState.Loading -> { /*do not send message til ack response*/ }
            else -> {
                Log.d("TIME123", "SOCKET CONNECTED?")

                val jsonOb = JSONObject().apply {
                    put("action", "TIP_USER");
                    put("sender", currentUserId.value);
                    put("receiver", selectedUserId.value);
                    put("value", "1")
                    put("previous", selectedUserSession.value)
                    put("sessionId", selectedUserSessionId.value)
                }

                Log.d("TIME123", "WEBSOCKET DATA:" + jsonOb.toString())

                //TODO:
                // ...check this over....
                ws?.let {
                    val send = it.send(jsonOb.toString());
                    println("TIME123 Sending message... SENT?" + send)
                    //IS THIS CORRECT??
                    if(!send){
                        _tipState.value = TipState.Loading
                    }
                }
            }
        }

    }

    override fun clearEvents(){
       // _uiStateEvent.value = EventUiState.DEFAULT;
        _tipState.value = TipState.Default
    }


    //TODO:
    // is this neccessary?>
    // SHould hold tokens, sessionIds, and cachedMessages, ack status
    sealed class ManagerState {
        //token
        //events (tips)
        //viewState
        //loginState
        //networkState???

    }

    sealed class TipState {
            object Default: TipState()
            data class Success(val message: JsonObject): TipState()
            object Loading: TipState()
            data class Error(val exception: Throwable): TipState()
    }

}