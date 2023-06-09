package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.details

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.farhan.tanvir.androidcleanarchitecture.AndroidCleanArchitecture
import com.farhan.tanvir.androidcleanarchitecture.MainActivity
import com.farhan.tanvir.androidcleanarchitecture.presentation.navigation.Screen
import com.farhan.tanvir.domain.model.User
import com.farhan.tanvir.domain.repository.UserRepository
import com.farhan.tanvir.domain.useCase.GetCurrentUserUseCase
import com.farhan.tanvir.domain.useCase.UserUseCases
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import org.json.JSONObject
import javax.inject.Inject
import com.farhan.tanvir.androidcleanarchitecture.util.Result
import com.farhan.tanvir.androidcleanarchitecture.util.SessionManager
import com.google.gson.JsonParser
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.*
import java.util.concurrent.Executors

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val userRepository: UserRepository,
    application: Application,
    @ApplicationContext context: Context
) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application.applicationContext)
    private val context1 = context

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Login)
    val uiState: StateFlow<LoginUiState> = _uiState
    private val _networkUiState = MutableStateFlow<NetworkUiState>(NetworkUiState.Neutral)

    private val _currentToken = MutableStateFlow<String>(((context1 as AndroidCleanArchitecture).getEncryptedPreferencesValue("userToken")) as String)
    val currentToken : StateFlow<String> = _currentToken

    val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjYzMWJhZWM3OTA0ZDQ3ZmExMzQ4YzgyZCIsInVzZXJuYW1lIjoiMTIxMzU1NTEyMTIiLCJleHBpcmUiOjE2ODI3NDQ5MDQ5Mzh9.WAnFXtzPFeWsff6iXv_zUF5CBZhdadbSzNcjgtRCLk0";


    //CONVERT TO FLOW
    //ON COLLECT IF STATE IS LOGIN.SUCCESS -> navigateToHOme


    fun postLogin(username: String, password:String) {
        viewModelScope.launch (
            Dispatchers.Main, CoroutineStart.DEFAULT
        ) {
            withContext(Dispatchers.IO) {
                //Show Loading
                _networkUiState.value = NetworkUiState.Loading
                //Send Request
                ////TODO: Should make a UseCaseFactory , implement invoke() method calls for injection / hoisting
                userUseCases.postLoginUseCase.username = username
                userUseCases.postLoginUseCase.password = password


                //Should set value as current user token in user repository in use case
                //This object should hold the network response (success/err/err)
                val response: JsonObject? = userUseCases.postLoginUseCase.invoke()

                //val response: Result<String> = userUseCases.postLoginUseCase.invoke()
                /* response?.get("token")?.let{
                           // _selectedToken.value = it.asString;
                println(_selectedToken.value)

            }*/

                //Remove Loading, Display Error
                when (response) {
                    //    is Result.Success -> networkUiState.value = NetworkUiState.Success
                    //is Response.Failure -> networkUiState.value = NetworkUiState.Failure(it.value)
                    //   is Result.Error -> networkUiState.value = NetworkUiState.Error(it.value)
                }

                response?.get("token")?.let {
                    // _selectedToken.value = it.asString;
                    //println(_selectedToken.value)
                    _networkUiState.value = NetworkUiState.Success

                    (getApplication<Application>().applicationContext as AndroidCleanArchitecture).currentUserToken =
                        it.asString;
                    //TODO: Convert to flow of userRepository (token/loggedInUser/getCurrentUser)
                    _uiState.value = LoginUiState.Home

                    sessionManager.saveAuthToken(it.asString)

                    (context1 as AndroidCleanArchitecture).setEncryptedPreferences("userToken", it.asString)

                } ?: run {
                    // _networkUiState.value = NetworkUiState.Failure(it.value)
                    _networkUiState.value = NetworkUiState.Failure("Network Error")
                }

                println("current user token = " + userRepository.getCurrentToken())
                if (userRepository.getCurrentToken() != null) {
                    _uiState.value = LoginUiState.Home

                    // navController.navigate(Screen.Home.route)
                }
            }
        }
    }

    fun postSignup(username:String, password:String){
        println("SIGNING UP WITH ${username} and ${password}")
    }

    fun postForgot(username:String){
        println("FORGOT ACCOUNT WITH ${username}")
    }

    fun getCurrentUser() {
       /* viewModelScope.launch {
            _selectedUser.value = userUseCases.getCurrentUserUseCase()
            // navController.navigate(route = Screen.Home.route)
            Log.d("TIME123", "New current user:" + _selectedUser.value)

        }*/
        viewModelScope.launch(
            Dispatchers.IO, CoroutineStart.DEFAULT
        ) {
            withNetworkModal(userUseCases.getCurrentUserUseCase,
                            {user: JsonObject -> showCurrentUser(user)},
                            {errorMsg: String -> showError(errorMsg)})
        }
    }

    fun showCurrentUser(user: JsonObject){

    }
    fun showError(msg: String){
        //toast - text
    }

    suspend fun withNetworkModal(wrappedContent: GetCurrentUserUseCase,
                                 successCallback: (JsonObject) -> Unit,
                                 errorCallback: (String) -> Unit){
            //Show Loading
            _networkUiState.value = NetworkUiState.Loading
            //Send Request
           // _selectedToken.value = wrappedContent()
            //Remove Loading, Display Data, Display Error
           // when (_selectedToken.value) {
                //Success -> networkUiState.value = NetworkUiState.Success ; successCallback(_selectedToken.value);
                //Failure -> networkUiState.value = NetworkUiState.Failure(errorMsg) ; errorCallback(errorMsg);
                //Unknown -> networkUiState.value = NetworkUiState.Failure("Unknown Error");
          //  }
    }

    fun showLogin(){
        _uiState.value = LoginUiState.Login
    }
    fun showSignup(){
        _uiState.value = LoginUiState.Signup
    }
    fun showForgot(){
        _uiState.value = LoginUiState.Forgot
    }

    sealed class LoginUiState {
        object Home: LoginUiState()
        object Login: LoginUiState()
        object Signup: LoginUiState()
        object Forgot: LoginUiState()
        data class Error(val exception: Throwable): LoginUiState()
    }

    sealed class NetworkUiState {
        object Neutral: NetworkUiState()
        object Loading: NetworkUiState()
        object Success: NetworkUiState()
        data class Failure(val error: String): NetworkUiState()
        data class Error(val exception: Throwable): NetworkUiState()
    }






    ////////FULL STATE-EVENT LIFECYCLE/////////

    data class LoginViewState(
        val email: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
        val error: String = ""
    )

    sealed class LoginViewEvent {
        data class EmailChanged(val email: String) : LoginViewEvent()
        data class PasswordChanged(val password: String) : LoginViewEvent()
        object LoginClicked : LoginViewEvent()
    }

    private val _state = MutableStateFlow(LoginViewState())
    val state : StateFlow<LoginViewState> = _state

    fun onEvent(event: LoginViewEvent) {
        when (event) {
            is LoginViewEvent.EmailChanged -> {
                _state.value = _state.value.copy(email = event.email)
            }
            is LoginViewEvent.PasswordChanged -> {
                _state.value = _state.value.copy(password = event.password)
            }
            is LoginViewEvent.LoginClicked -> {
                // Perform login logic
                postLogin(_state.value.email, _state.value.password)
                // Update state based on the result
                //in postLogin?/////////////////////
            }
        }
    }
}