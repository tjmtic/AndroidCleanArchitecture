package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.*
import com.farhan.tanvir.androidcleanarchitecture.AndroidCleanArchitecture
import com.farhan.tanvir.androidcleanarchitecture.MainActivity
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.details.LoginViewModel
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.details.UserDetailsViewModel
import com.farhan.tanvir.androidcleanarchitecture.util.SocketHandler
import com.farhan.tanvir.domain.model.User
import com.farhan.tanvir.domain.useCase.UserUseCases
import com.google.gson.JsonArray
import com.google.gson.JsonElement
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
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import java.util.concurrent.Executors
import javax.inject.Inject
import kotlinx.serialization.*
//import kotlinx.serialization.json.*

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

    val _historyUsers: MutableStateFlow<JsonArray?> = MutableStateFlow(JsonArray())
    val historyUsers: MutableStateFlow<JsonArray?> = _historyUsers

    val _contributorUsers: MutableStateFlow<JsonObject?> = MutableStateFlow(JsonObject())
    val contributorUsers: MutableStateFlow<JsonObject?> = _contributorUsers

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
    private val _currentUser: MutableStateFlow<JsonObject?> = MutableStateFlow(JsonObject())
    val currentUser: MutableStateFlow<JsonObject?> = _currentUser

    private val _selectedUser: MutableStateFlow<JsonObject?> = MutableStateFlow(JsonObject())
    val selectedUser: MutableStateFlow<JsonObject?> = _selectedUser
    val selectedUserToken : MutableStateFlow<String?> = MutableStateFlow("");

    private val _selectedUserSession: MutableStateFlow<String> = MutableStateFlow("")
    val selectedUserSession: MutableStateFlow<String> = _selectedUserSession
    private val _selectedUserSessionId: MutableStateFlow<String> = MutableStateFlow("")
    val selectedUserSessionId: MutableStateFlow<String> = _selectedUserSessionId

    private val _disabled: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val _currentToken = MutableStateFlow<String>(((application.applicationContext as AndroidCleanArchitecture).getEncryptedPreferencesValue("userToken")) as String)
    val token : StateFlow<String> = _currentToken

   // private val _socketToken = MutableStateFlow<String>(((application.applicationContext as AndroidCleanArchitecture).currentUserSocketId ) as String)
   // val socketToken : StateFlow<String> = _socketToken
    //val token = (getApplication<Application>().applicationContext as AndroidCleanArchitecture).currentUserToken

    val qrImage: Bitmap? = token?.let{(getApplication<Application>().applicationContext as AndroidCleanArchitecture).generateQR(token.value)}





    init {
        Log.d("TIME123","initializeing homewVIEWMODEL....0");
        viewModelScope.launch {
            Log.d("TIME123","initializeing homewVIEWMODEL....1");

            token.let {
                Log.d("TIME123","initializeing homewVIEWMODEL....2");

                _currentUser.value = userUseCases.getCurrentUserWithTokenUseCase(token.value)
                Log.d("TIME123","initializeing homewVIEWMODEL....3");


                _historyUsers.apply{
                    value = _currentUser.value?.get("history")?.asJsonArray
                }

                //_allUsers.value = userUseCases.getAllUsersWithTokenUseCase(token.value)

                //val users: JsonArray? = userUseCases.getAllUsersWithTokenUseCase(token.value)
                //var ju = JsonObject();
                //ju.add("contributors", users);

                _allUsers.apply{
                    Log.d("TIME123","initializeing homewVIEWMODEL....4");

                    //val users: JsonArray? = userUseCases.getAllUsersWithTokenUseCase(token.value)
                    //var ju = JsonObject();
                    //var ja = JsonArray();
                    historyUsers.value?.let { it1 ->
                        userUseCases.getUsersByIdUseCase(it1,it1, token.value)?.let { users ->
                            Log.d("TIME123","initializeing homewVIEWMODEL....5");

                            //this.value =
                            val ja = JsonArray().also { array ->
                                Log.d("TIME123","initializeing homewVIEWMODEL....6");
                                try {
                                users.get("history").asJsonArray.mapNotNull {
                                        array.add(JsonObject()
                                            .apply {
                                                addProperty(
                                                    "name",
                                                    it.asJsonObject.get("name").asString
                                                )
                                                addProperty(
                                                    "_id",
                                                    it.asJsonObject.get("_id").asString
                                                )
                                                add(
                                                    "images",
                                                    it.asJsonObject.get("images").asJsonArray
                                                )
                                            })

                                }
                                } catch (e: Exception) {
                                    Log.d("TIME123", "Error:" + e.message)
                                    null
                                }
                            }

                            Log.d("TIME123","initializeing homewVIEWMODEL....8" + ja.toString());


                            /*val ju = JsonObject().also{ also ->
                                                also.add("contributors",ja)
                                            }
                                            ju*/

                            /* JsonObject().also{ also ->
                                                also.add("contributors",ja)
                                            }*/

                            value = JsonObject().also{
                                it.add("history", ja)
                                it.add("contributors", ja)
                            }
                        }
                    } ?: run{
                        Log.d("TIME123","initializeing homewVIEWMODEL....9");

                        performLogout();
                    }
                }
               /* users?.let {

                    var jt: List<JsonObject> = users.map {
                        try {
                            var j = JsonObject();
                            j.addProperty("email", it.asJsonObject.get("email").asString)
                            // j.addProperty("phoneNumber", it.asJsonObject.get("phoneNumber").asString)
                            //  j.addProperty("name", it.asJsonObject.get("name").asString)
                            //j.addProperty("balance", it.asJsonObject.get("balance").asInt)
                            //j.addProperty("images", it.asJsonArray.get("images"))

                            j.apply {
                                addProperty("_id", it.asJsonObject.get("_id").asString)
                                add("images", it.asJsonObject.get("images").asJsonArray)
                            }

                        } catch (e: Exception) {
                            Log.d("TIME123", "Exception:" + e.message)
                            var j = JsonObject();
                            // j.addProperty("phoneNumber", it.asJsonObject.get("phoneNumber").asString)
                            //  j.addProperty("name", it.asJsonObject.get("name").asString)
                            //j.addProperty("balance", it.asJsonObject.get("balance").asInt)
                            //j.addProperty("images", it.asJsonArray.get("images"))

                            j.apply {
                                addProperty("email", "none");
                            }
                        }
                    } as List<JsonObject>
                    // var je = JsonObject();
                    // _allUsers.value = ;
                    //val jarray = listOf
                    //ju.addProperty("contributors", JsonArray.from(jt));

                    val jsonArray = JsonArray().apply {
                        (jt).forEach { it: JsonObject ->
                            if (!it.get("email").asString.equals("none")) {
                                Log.d("TIME123", "GETTING ALL EMAIL 1" + it.get("email").asString);
                                this.add(it)
                            }
                        }
                    }

                    Log.d("TIME123", "GETTING ALL USERS 1");
                    Log.d("TIME123", _allUsers.value.toString());
                    Log.d("TIME123", jsonArray.toString());
                    Log.d("TIME123", "GETTING ALL USERS X");
                    ju.add("contributors", jsonArray);
                    _allUsers.value = ju;
                }?:run{
                    (getApplication<Application>().applicationContext as AndroidCleanArchitecture).logout()
                    _uiStateLogin.value = LoginUiState.Invalid("Invalid User Token")
                }*/

                // navController.navigate(route = Screen.Home.route)
                Log.d("TIME123","initializeing homewVIEWMODEL....10");

                Log.d("TIME123", "New current user:" + _currentUser.value)

                //user id to set socket namespace
                //MainActivity.setSocketNamespace(userId)
                _currentUser.value?.get("socketId")?.let {
                    (getApplication<Application>().applicationContext as AndroidCleanArchitecture).currentUserSocketId =
                        it.asString;
                }


                start();
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
                jsonObject["hash"]?.let{
                    _selectedUserSession.value = it.asString

                    _disabled.value = false
                };
            }
            "REFRESH" -> {
                // Handle the REFRESH action
                println("Socket Action: REFRESH")
                _uiStateEvent.value = EventUiState.DEFAULT;
                jsonObject["previous"]?.let{
                    _selectedUserSession.value = it.asString
                };

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
    }

    fun sendMessage(ws: WebSocket?){
        _disabled.value = true
        Log.d("TIME123", "SOCKET CONNECTED?")
        //val send = ws?.send("ANDROID MESSAGE SENT");
        var jsonOb = JSONObject();
        jsonOb.put("action", "TIP_USER");
        jsonOb.put("sender", currentUser.value?.get("id")?.asString);
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
    }

    fun clearEvents(){
        _uiStateEvent.value = EventUiState.DEFAULT;
    }



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

        //selectedUserToken = id;
        //sendMessage(ws);
        viewModelScope.launch {
            Log.d("TIME123","SELECTION BY ID....1 "+id);

            token.let {
                val respo = userUseCases.getUserByIdUseCase(id , token.value)

                val data = JsonObject().also{
                    it.addProperty("sender", currentUser.value?.get("id")?.asString )
                    it.addProperty("receiver", id )
                }

                val respo2 = userUseCases.createSessionByUserUseCase(data , token.value)

                respo2?.get("previous")?.let{
                _selectedUserSession.value = it.asString
            };

                respo2?.get("_id")?.let{
                    _selectedUserSessionId.value = it.asString
                };

                Log.d("TIME123","SELECTION BY ID....2 "+respo.toString());

                Log.d("TIME123","SELECTION BY ID....2A "+data.toString()+respo2.toString());


                _selectedUser.value = respo

            }

            Log.d("TIME123","SELECTION BY ID....3 ");

        }

        /*_allUsers.value?.get("contributors")?.let {
            println("USER ELEMENT ${it}")

            for (item in it as JsonArray) {
                println(" ${id} USER ARRAY ${item}")

                if((item as JsonObject).get("id").asString.equals(id)){
                    _selectedUser.value = item
                    println("SET SELECTED USER OBJECT ${item}")

                    //update websocket connection??
                }
                else{
                    println((item as JsonObject).get("id").asString)
                    println(id)
                }
            }
        }*/
    }

    fun setUnselectedUser(){
        _selectedUser.value = JsonObject();
    }

    fun performLogout(){
        (getApplication<Application>().applicationContext as AndroidCleanArchitecture).logout()
        _uiStateLogin.value = LoginUiState.Invalid("Invalid User Token")
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