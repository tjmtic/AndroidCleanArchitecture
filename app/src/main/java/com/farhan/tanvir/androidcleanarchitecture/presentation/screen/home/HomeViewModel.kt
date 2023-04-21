package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.*
import com.farhan.tanvir.androidcleanarchitecture.AndroidCleanArchitecture
import com.farhan.tanvir.androidcleanarchitecture.MainActivity
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.details.LoginViewModel
import com.farhan.tanvir.androidcleanarchitecture.util.SocketHandler
import com.farhan.tanvir.domain.model.User
import com.farhan.tanvir.domain.useCase.UserUseCases
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject
import java.util.*
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    application: Application
) : AndroidViewModel(application) {
    //val getAllUsers = userUseCases.getAllUsersUseCase()
    val _allUsers: MutableStateFlow<JsonObject?> = MutableStateFlow(JsonObject())
    val allUsers: MutableStateFlow<JsonObject?> = _allUsers
    val sendUsers: MutableStateFlow<JsonObject?> = _allUsers
    val receiveUsers: MutableStateFlow<JsonObject?> = _allUsers

    //val userSocketId: String;
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Default)
    val uiState: StateFlow<HomeUiState> = _uiState

    private val _uiStateCamera = MutableStateFlow<CameraUiState>(CameraUiState.Disabled)
    val uiStateCamera: StateFlow<CameraUiState> = _uiStateCamera

    private val _uiStateEvent = MutableStateFlow<EventUiState>(EventUiState.DEFAULT)
    val uiStateEvent: StateFlow<EventUiState> = _uiStateEvent

    //private val _currentUser: MutableLiveData<User> = MutableLiveData(null)
    private val _currentUser: MutableStateFlow<JsonObject?> = MutableStateFlow(JsonObject())
    val currentUser: MutableStateFlow<JsonObject?> = _currentUser

    private val _selectedUser: MutableStateFlow<JsonObject?> = MutableStateFlow(JsonObject())
    val selectedUser: MutableStateFlow<JsonObject?> = _selectedUser


    private val _currentToken = MutableStateFlow<String>(((application.applicationContext as AndroidCleanArchitecture).getEncryptedPreferencesValue("userToken")) as String)
    val token : StateFlow<String> = _currentToken

    private val _socketToken = MutableStateFlow<String>(((application.applicationContext as AndroidCleanArchitecture).currentUserSocketId ) as String)
    val socketToken : StateFlow<String> = _socketToken
    //val token = (getApplication<Application>().applicationContext as AndroidCleanArchitecture).currentUserToken

    val qrImage: Bitmap? = token?.let{(getApplication<Application>().applicationContext as AndroidCleanArchitecture).generateQR(token.value)}





    init {
        Log.d("TIME123","initializeing homewVIEWMODEL....");
        viewModelScope.launch {
            token?.let {
                _currentUser.value = userUseCases.getCurrentUserWithTokenUseCase(token.value)
                _allUsers.value = userUseCases.getAllUsersWithTokenUseCase(token.value)
            }

            // navController.navigate(route = Screen.Home.route)
            Log.d("TIME123", "New current user:" + _currentUser.value)

            //user id to set socket namespace
            //MainActivity.setSocketNamespace(userId)
            _currentUser.value?.get("socketId")?.let {
                (getApplication<Application>().applicationContext as AndroidCleanArchitecture).currentUserSocketId =
                    it.asString;
            }
        }
    }


    var ws: WebSocket? = null
    val client by lazy { OkHttpClient() }

    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }

    var thread = Executors.newSingleThreadExecutor()
    //val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjYzMWJhZWM3OTA0ZDQ3ZmExMzQ4YzgyZCIsInVzZXJuYW1lIjoiMTIxMzU1NTEyMTIiLCJleHBpcmUiOjE2ODI3NDQ5MDQ5Mzh9.WAnFXtzPFeWsff6iXv_zUF5CBZhdadbSzNcjgtRCLk0";


    fun start() {

        val request: Request =
            //   Request.Builder().url("ws://34.122.212.113/").build()
            Request.Builder().url("ws://3.239.168.240").addHeader("Authorization", "Bearer ${socketToken.value}").build()
        //Request.Builder().url("ws://10.0.2.2:8082").addHeader("Authorization", "Bearer $token").build()

        val listener = object: WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                val currentTime: Date = Calendar.getInstance().time
                //exampleUiState.addMessage(
                //   Message("Web", text, currentTime.toString()))
                Log.d("TIME123", "From SOCKET:" + text)

                handleReceivedText(text)

            }
        }

        viewModelScope.launch(thread.asCoroutineDispatcher() + coroutineExceptionHandler, CoroutineStart.DEFAULT) {
            ws = client.newWebSocket(request, listener)

            Log.d("TIME123", "SOCKET CONNECTED?")
            //val send = ws?.send("ANDROID MESSAGE SENT");
            var jsonOb = JSONObject();
            jsonOb.put("action", "SEND_TIP");
            jsonOb.put("from", token);
            jsonOb.put("text", '1')

            val send = ws?.send(jsonOb.toString());

            ws?.let {
                println("TIME123 Sending message... SENT?" + send)
            }
        }

    }




    private fun handleReceivedText(text: String) {
        // 1. Parse the incoming text as a JSON object
        // Replace this part with your JSON parsing library if you use a different one
        val jsonObject = JsonParser.parseString(text).asJsonObject

        // 2. Extract the 'action' property from the JSON object
        val action = jsonObject["action"]?.asString

        // 3. Use the extracted 'action' in the when statement
        when (action) {
            // Handle actions here
            "RECEIVER_TIP" -> {
                // Handle the RECEIVER_TIP action
                println("Socket Action: RECEIVER_TIP")
                _uiStateEvent.value = EventUiState.TIP;

            }
            "RECEIVE_MESSAGE" -> {
                // Handle the RECEIVER_MESSAGE action
                println("Socket Action: RECEIVER_MESSAGE")
                _uiStateEvent.value = EventUiState.TIP;

            }
            "RECEIVER_TIP_RAIN" -> {
                // Handle the RECEIVER_TIP_RAIN action
                println("Socket Action: RECEIVER_TIP_RAIN")
                _uiStateEvent.value = EventUiState.RAIN;
            }
            "THANK_YOU" -> {
                // Handle the THANK_YOU action
                println("Socket Action: THANK_YOU")
                _uiStateEvent.value = EventUiState.DEFAULT;
            }
            "ACK" -> {
                // Handle the ACK action
                println("Socket Action: ACK")
                _uiStateEvent.value = EventUiState.DEFAULT;
            }
            "REFRESH" -> {
                // Handle the REFRESH action
                println("Socket Action: REFRESH")
                _uiStateEvent.value = EventUiState.DEFAULT;
            }
            "RECEIVER_TIP_BAG" -> {
                // Handle the RECEIVER_TIP_BAG action
                println("Socket Action: RECEIVER_TIP_BAG")
                _uiStateEvent.value = EventUiState.TIP;
            }
            else -> {
                println("Unknown action")
                _uiStateEvent.value = EventUiState.DEFAULT;
            }
        }
    }

    fun clearEvents(){
        _uiStateEvent.value = EventUiState.DEFAULT;
    }



    fun showSend(){
        _uiState.value = HomeUiState.Send
    }

    fun showReceive(){
        _uiState.value = HomeUiState.Receive
    }

    fun showDefault(){
        _uiState.value = HomeUiState.Default
    }

    fun showCamera(){
        _uiStateCamera.value = CameraUiState.Enabled
    }

    fun hideCamera(){
        _uiStateCamera.value = CameraUiState.Disabled
    }

    fun toggleCamera(){
        when(_uiStateCamera.value){
            is CameraUiState.Enabled -> _uiStateCamera.value = CameraUiState.Disabled
            is CameraUiState.Disabled -> _uiStateCamera.value = CameraUiState.Enabled
        }
    }

    fun setSelectedById(id: String){
        _allUsers.value?.get("receivers")?.let {
            println("USER ELEMENT ${it}")

            for (item in it as JsonArray) {
                println(" ${id} USER ARRAY ${item}")

                if((item as JsonObject).get("id").asString.equals(id)){
                    _selectedUser.value = item
                    println("SET SELECTED USER OBJECT ${item}")
                }
                else{
                    println((item as JsonObject).get("id").asString)
                    println(id)
                }
            }
        }
    }

    sealed class EventUiState {
        object DEFAULT: EventUiState()
        object TIP: EventUiState()
        object RAIN: EventUiState()
        data class Error(val exception: Throwable): EventUiState()
    }


    sealed class HomeUiState {
        object Default: HomeUiState()
        object Send: HomeUiState()
        object Receive: HomeUiState()
        data class Error(val exception: Throwable): HomeUiState()
    }

    sealed class CameraUiState {
        object Enabled: CameraUiState()
        object Disabled: CameraUiState()
        data class Error(val exception: Throwable): CameraUiState()
    }

}