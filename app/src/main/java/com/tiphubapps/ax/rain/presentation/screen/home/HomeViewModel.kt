package com.tiphubapps.ax.rain.presentation.screen.home

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.*
import com.google.gson.Gson
import com.tiphubapps.ax.rain.Rain
import com.tiphubapps.ax.domain.model.User
import com.tiphubapps.ax.domain.useCase.UserUseCases
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.tiphubapps.ax.data.util.CoroutineContextProvider
import com.tiphubapps.ax.domain.repository.UseCaseResult
import com.tiphubapps.ax.data.util.SessionManager
import com.tiphubapps.ax.data.util.WebSocketManager
import com.tiphubapps.ax.domain.repository.AuthRepository
import com.tiphubapps.ax.domain.useCase.AuthUseCases
import com.tiphubapps.ax.rain.presentation.screen.login.AuthedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject
import java.util.*
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Named

//import kotlinx.serialization.json.*

@HiltViewModel
class HomeViewModel @Inject constructor(
    @Named("suite") private val userUseCases: UserUseCases,
    private val authUseCases: AuthUseCases,
    private val coroutineContextProvider: CoroutineContextProvider,
    //private val sessionManager: SessionManager,
   // private val authRepository: AuthRepository,
    private val webSocketManager: WebSocketManager,
    //private val authViewModel: AuthedViewModel,
    //application: Application
) : AuthedViewModel(authUseCases, coroutineContextProvider) {


    //val isLoggedIn = true//authViewModel.isLoggedIn

    //val getAllUsers = userUseCases.getAllUsersUseCase()
    private val _allUsers: MutableStateFlow<JsonObject?> = MutableStateFlow(JsonObject())
    val allUsers: StateFlow<JsonObject?> = _allUsers
    //val sendUsers: StateFlow<JsonObject?> = _allUsers
    //val receiveUsers: StateFlow<JsonObject?> = _allUsers

    private val _historyUsers: MutableStateFlow<JsonArray?> = MutableStateFlow(JsonArray())
    val historyUsers: StateFlow<JsonArray?> = _historyUsers

    private val _contributorUsers: MutableStateFlow<JsonArray?> = MutableStateFlow(JsonArray())
    val contributorUsers: StateFlow<JsonArray?> = _contributorUsers

    //val userSocketId: String;
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Default)
    val uiState: StateFlow<HomeUiState> = _uiState

    private val _uiStateCamera = MutableStateFlow<CameraUiState>(CameraUiState.Disabled)
    val uiStateCamera: StateFlow<CameraUiState> = _uiStateCamera

    private val _uiStateLogin = MutableStateFlow<LoginUiState>(LoginUiState.Default)
    val uiStateLogin: StateFlow<LoginUiState> = _uiStateLogin

    private val _uiStateEvent = MutableStateFlow<EventUiState>(EventUiState.DEFAULT)
    val uiStateEvent: StateFlow<EventUiState> = _uiStateEvent

    //private val _currentUser: MutableLiveData<User> = MutableLiveData(null)
    private val _currentUser: MutableStateFlow<User?> = MutableStateFlow(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _selectedUser: MutableStateFlow<User?> = MutableStateFlow(null)
    val selectedUser: StateFlow<User?> = _selectedUser
    //val selectedUserToken : StateFlow<String?> = MutableStateFlow("");

    //private val _selectedUserSession: MutableStateFlow<String> = MutableStateFlow("")
    //val selectedUserSession: StateFlow<String> = _selectedUserSession
    //private val _selectedUserSessionId: MutableStateFlow<String> = MutableStateFlow("")
    //val selectedUserSessionId: StateFlow<String> = _selectedUserSessionId

    private val _disabled: MutableStateFlow<Boolean> = MutableStateFlow(false)

    //private val _currentToken = MutableStateFlow<String>((sessionManager.getUserToken()) as String)
    //val token : StateFlow<String> = _currentToken

   // private val _socketToken = MutableStateFlow<String>(((application.applicationContext as AndroidCleanArchitecture).currentUserSocketId ) as String)
   // val socketToken : StateFlow<String> = _socketToken
    //val token = (getApplication<Application>().applicationContext as AndroidCleanArchitecture).currentUserToken


    val qrImage: Bitmap? = null//token?.let{(getApplication<Application>().applicationContext as Rain).generateQR(token.value)}





    init {
        viewModelScope.launch {

            //if(token.value.isNotEmpty()) {

                //_currentUser.value = userUseCases.getCurrentUserWithTokenUseCase(token.value)
                val cu = userUseCases.getCurrentUserUseCase!!()
                when(cu){
                    is UseCaseResult.UseCaseSuccess -> _currentUser.value = cu.data//JsonObject().apply{ addProperty("user", Gson().toJson(cu.data)) }
                    else -> {}
                }

                _historyUsers.apply{
                    value = _currentUser.value?.history//get("history")?.asJsonArray
                }

                _contributorUsers.apply{
                    value = _currentUser.value?.contributors//get("history")?.asJsonArray
                }

                _allUsers.apply{

                    historyUsers.value?.let { it1 ->
                        userUseCases.getUsersByIdUseCase!!(it1,it1)?.let { users ->

                            //this.value =
                            val ja = JsonArray().also { array ->
                                try {
                                    when(users){

                                        is UseCaseResult.UseCaseSuccess -> {
                                            users.data.mapNotNull {
                                                array.add(JsonObject()
                                                    .apply {
                                                        addProperty(
                                                            "name",
                                                            it.name
                                                        )
                                                        addProperty(
                                                            "_id",
                                                            it.id
                                                        )
                                                        add(
                                                            "images",
                                                            it.images
                                                        )
                                                    })
                                            }
                                        }

                                        else -> {}
                                    }
                                } catch (e: Exception) {
                                    Log.d("TIME123", "Error:" + e.message)
                                    null
                                }
                            }


                            value = JsonObject().also{
                                it.add("history", ja)
                                it.add("contributors", ja)
                            }
                        }
                   // } ?: run{

                   //     performLogout();
                   // }
                }

               /* webSocketManager.setListener(object: WebSocketListener() {
                    override fun onMessage(webSocket: WebSocket, text: String) {
                        val currentTime: Date = Calendar.getInstance().time
                        //exampleUiState.addMessage(
                        //   Message("Web", text, currentTime.toString()))
                        Log.d("TIME123", "From HOMEVIEWMODEL:" + text)

                        handleReceivedText(text)
                        //vibrate
                        //show messages
                        //play animation
                    }
                })

                webSocketManager.connect()*/

                    websocketInit()


            }
            //else{
            //    performLogout()
            //}
        }
    }

    suspend fun websocketInit(){
            webSocketManager.setListener(object : WebSocketListener() {
                override fun onMessage(webSocket: WebSocket, text: String) {
                    val currentTime: Date = Calendar.getInstance().time
                    //exampleUiState.addMessage(
                    //   Message("Web", text, currentTime.toString()))
                    Log.d("TIME123", "From HOMEVIEWMODEL:" + text)

                    handleReceivedText(text)
                    //vibrate
                    //show messages
                    //play animation
                }
            })

        //viewModelScope.launch(coroutineContextProvider.io, CoroutineStart.DEFAULT) {

            webSocketManager.connect()
        //}
    }

    fun sendWsMessage(amount: Int){
        viewModelScope.launch {
            webSocketManager.isConnected.collectLatest {
                if (it) {
                    webSocketManager.sendMessage("test")
                }
                else{
                    webSocketManager.connect()
                }
            }
        }
    }


    //var ws: WebSocket? = null
    //val client by lazy { OkHttpClient() }

    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }

    var thread = Executors.newSingleThreadExecutor()
    //val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjYzMWJhZWM3OTA0ZDQ3ZmExMzQ4YzgyZCIsInVzZXJuYW1lIjoiMTIxMzU1NTEyMTIiLCJleHBpcmUiOjE2ODI3NDQ5MDQ5Mzh9.WAnFXtzPFeWsff6iXv_zUF5CBZhdadbSzNcjgtRCLk0";


    /*fun start() {

        val request: Request =
            //   Request.Builder().url("ws://34.122.212.113/").build()
            Request.Builder().url("ws://3.239.168.240").addHeader("Authorization", "Bearer ${token.value}").build()
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
           /* var jsonOb = JSONObject();
            jsonOb.put("action", "SEND_TIP");
            jsonOb.put("from", token);
            jsonOb.put("text", '1')

            val send = ws?.send(jsonOb.toString());

            ws?.let {
                println("TIME123 Sending message... SENT?" + send)
            }*/

            //sendMessage(ws)
        }

    }*/




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
                //_uiStateEvent.value = EventUiState.DEFAULT;
                //jsonObject["hash"]?.let{
                //    _selectedUserSession.value = it.asString

                    //webSocketManager.selectedUserSession = it.asString

                    _disabled.value = false
               // };
            }
            "REFRESH" -> {
                // Handle the REFRESH action
                println("Socket Action: REFRESH")
                _uiStateEvent.value = EventUiState.DEFAULT;
                //jsonObject["previous"]?.let{
                //    _selectedUserSession.value = it.asString
                //};

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
/*
    fun sendTip(){

       // if(!_disabled.value) {
            viewModelScope.launch(
                thread.asCoroutineDispatcher() + coroutineExceptionHandler,
                CoroutineStart.DEFAULT
            ) {

                val request: Request =
                    //   Request.Builder().url("ws://34.122.212.113/").build()
                    Request.Builder().url("ws://3.239.168.240")
                        .addHeader("Authorization", "Bearer ${token.value}").build()
                //Request.Builder().url("ws://10.0.2.2:8082").addHeader("Authorization", "Bearer $token").build()

                val listener = object : WebSocketListener() {
                    override fun onMessage(webSocket: WebSocket, text: String) {
                        val currentTime: Date = Calendar.getInstance().time
                        //exampleUiState.addMessage(
                        //   Message("Web", text, currentTime.toString()))
                        Log.d("TIME123", "From SOCKET:" + text)

                        handleReceivedText(text)

                    }
                }

                ws = client.newWebSocket(request, listener)
                sendMessage(ws)
            }
       // }
        //sendMessage(ws)
    }*/
/*
    fun sendMessage(ws: WebSocket?){
        _disabled.value = true
        Log.d("TIME123", "SOCKET CONNECTED?")
        //val send = ws?.send("ANDROID MESSAGE SENT");
        var jsonOb = JSONObject();
        jsonOb.put("action", "TIP_USER");
        jsonOb.put("sender", currentUser.value?.id)//get("id")?.asString);
        jsonOb.put("receiver", selectedUser.value?.get("id")?.asString);
        jsonOb.put("value", "1")
        jsonOb.put("previous", selectedUserSession.value)
        jsonOb.put("sessionId", selectedUserSessionId.value)

        Log.d("TIME123", "WEBSOCKET DATA:" + jsonOb.toString())

        val send = ws?.send(jsonOb.toString());

        ws?.let {
            println("TIME123 Sending message... SENT?" + send)

            if(!send!!){
                _disabled.value = false
            }
        }
    }*/

    fun clearEvents(){
        _uiStateEvent.value = EventUiState.DEFAULT;
    }

    ////////UI VIEWS/////////////////////////
    fun showSend(){
        _uiState.value = HomeUiState.Send

        Log.d("TIME123", "Logging State:");
        Log.d("TIME123", currentUser.value.toString() )
        Log.d("TIME123", selectedUser.value.toString() )
    }
    fun showReceive(){
        _uiState.value = HomeUiState.Receive
    }
    fun showDefault(){
        _uiState.value = HomeUiState.Default
    }
    /////////////////////////////////////////


    ///////////////CAMERA VIEWS//////////////
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
            else -> _uiStateCamera.value = CameraUiState.Disabled
        }
    }
    ////////////////////////////////////////////
    fun setSelectedById(id: String){

        //selectedUserToken = id;
        //sendMessage(ws);
        viewModelScope.launch {
            Log.d("TIME123","SELECTION BY ID....1 "+id);

            //token.let {
                val respo = userUseCases.getUserByIdUseCase!!(id)

                val respo2 = userUseCases.createSessionByUserUseCase!!(id )

                respo2?.get("previous")?.let{
                    //TODO WEBSOCKET SET VALUE
                    webSocketManager.setSelectedUserSession(it.asString)
                //_selectedUserSession.value = it.asString
            };

                respo2?.get("_id")?.let{
                    //TODO WEBSOCKET SET VALUE
                    webSocketManager.setSelectedUserSessionId(it.asString)
                   // _selectedUserSessionId.value = it.asString
                };

                Log.d("TIME123","SELECTION BY ID....2 "+respo.toString());

                when(respo){
                    is UseCaseResult.UseCaseSuccess -> _selectedUser.value = respo.data
                    else -> {}
                }
        }

    }

    fun setUnselectedUser(){
        _selectedUser.value = null
    }

    fun performLogout(){
        println("TIME123 Logging Out...")
        //authViewModel.logout()

        //(getApplication<Application>().applicationContext as Rain).logout()
        _uiStateLogin.value = LoginUiState.Invalid("Invalid User Token")
    }

    sealed class ViewModelState {
        //token
        //events (tips)
        //viewState
        //cameraState
        //loginState
        //networkState???

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

    sealed class LoginUiState {
        object Default: LoginUiState()
        data class Valid(val user: User): LoginUiState()
        data class Invalid(val reason: String): LoginUiState()
        data class Error(val exception: Throwable): LoginUiState()
    }

}