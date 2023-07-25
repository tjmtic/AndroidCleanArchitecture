package com.tiphubapps.ax.rain.presentation.screen.details

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.*
import com.tiphubapps.ax.rain.Rain
import com.tiphubapps.ax.domain.repository.UserRepository
import com.tiphubapps.ax.domain.useCase.GetCurrentUserUseCase
import com.tiphubapps.ax.domain.useCase.UserUseCases
import com.google.gson.JsonObject
import com.tiphubapps.ax.domain.repository.AppError
import com.tiphubapps.ax.domain.repository.Result
import com.tiphubapps.ax.rain.presentation.helper.performVibration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import com.tiphubapps.ax.rain.util.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val userRepository: UserRepository,
    application: Application,
    @ApplicationContext context: Context
) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application.applicationContext)
    private val context1 = context


    //Not necessary from addition of "_state"
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Login)
    val uiState: StateFlow<LoginUiState> = _uiState
    private val _networkUiState = MutableStateFlow<NetworkUiState>(NetworkUiState.Neutral)
    val networkUiState: StateFlow<NetworkUiState> = _networkUiState
    ////////////


    /////TODO: Finalize token handling...1///
    private val _currentToken = MutableStateFlow<String>(((context1 as Rain).getEncryptedPreferencesValue("userToken")) as String)
    val currentToken : StateFlow<String> = _currentToken

    val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjYzMWJhZWM3OTA0ZDQ3ZmExMzQ4YzgyZCIsInVzZXJuYW1lIjoiMTIxMzU1NTEyMTIiLCJleHBpcmUiOjE2ODI3NDQ5MDQ5Mzh9.WAnFXtzPFeWsff6iXv_zUF5CBZhdadbSzNcjgtRCLk0";
    /////////////////////////////////////////

    //CONVERT TO FLOW
    //ON COLLECT IF STATE IS LOGIN.SUCCESS -> navigateToHOme

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val errorMessage = throwable.message ?: "An error occurred"
        showToast(errorMessage)
    }
    fun postLogin(username: String, password:String) {
        viewModelScope.launch (
            Dispatchers.Main, CoroutineStart.DEFAULT
        ) {
            withContext(Dispatchers.IO) {
                //Show Loading
                _networkUiState.value = NetworkUiState.Loading
                _state.value = _state.value.copy(isLoading = true)

                //Remove Loading, Display Error or Success
                when (val response: Result<List<JsonObject>> = userUseCases.useCaseLogin(username, password)) {
                       is Result.Success -> (response.data as JsonObject).get("token").let {


                           ////TODO: Finalize token handling...2////////////
                           (getApplication<Application>().applicationContext as Rain).currentUserToken =
                               it.asString;
                           //TODO: Convert to flow of userRepository (token/loggedInUser/getCurrentUser)
                           _uiState.value = LoginUiState.Home

                           sessionManager.saveAuthToken(it.asString)

                           (context1 as Rain).setEncryptedPreferences("userToken", it.asString)
                           /////////////////////////////////////////////////

                           _networkUiState.value = NetworkUiState.Success


                       }
                       is Result.Error -> {
                                            _networkUiState.value = NetworkUiState.Failure(response.error.toString())
                                            handleError(response.error)
                       }
                }


                _state.value = _state.value.copy(isLoading = false)


                //TODO: Convert to use case
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

    fun handleError(error: AppError){
        performVibration(context = context1)


        val errorMessage = when(error){
            is AppError.NetworkError, AppError.ServerError  -> {
                "Service Issue."
            }

            is AppError.InputError  -> {
                "Input Error."
            }

            is AppError.CustomError -> {
                error.errorMessage
            }
        }
        _state.value = _state.value.copy(error = errorMessage, errors = _state.value.errors.plus(errorMessage))
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
        showToast(msg)
    }

    private fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        context1.let {
            Toast.makeText(it, message, duration).show()
        }
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



    //Should remove these? Extraneous UiState or relevant for other operations?
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

/////////////////////////////////////////////////////////////////////////




    ////////FULL STATE-EVENT LIFECYCLE/////////


    data class LoginViewState(
        val name: String = "",
        val email: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
        val error: String = "",
        val errors: List<String> = emptyList()
    )

    private val _state = MutableStateFlow(LoginViewState())
    val state : StateFlow<LoginViewState> = _state

    sealed class LoginViewEvent {
        data class EmailChanged(val email: String) : LoginViewEvent()
        data class PasswordChanged(val password: String) : LoginViewEvent()
        data class NameChanged(val name: String) : LoginViewEvent()
        object LoginClicked : LoginViewEvent()
        object SignupClicked : LoginViewEvent()
        object ForgotClicked : LoginViewEvent()
       // data class CreateError(val name: String) : LoginViewEvent()
        object ConsumeError : LoginViewEvent()
    }
    fun onEvent(event: LoginViewEvent) {
        when (event) {
            is LoginViewEvent.EmailChanged -> {
                _state.value = _state.value.copy(email = event.email)
            }
            is LoginViewEvent.PasswordChanged -> {
                _state.value = _state.value.copy(password = event.password)
            }
            is LoginViewEvent.NameChanged -> {
                _state.value = _state.value.copy(name = event.name)
            }
            is LoginViewEvent.LoginClicked -> {
                postLogin(_state.value.email, _state.value.password)
            }
            is LoginViewEvent.SignupClicked -> {
                postSignup(_state.value.email, _state.value.password)
            }
            is LoginViewEvent.ForgotClicked -> {
                postForgot(_state.value.email)
            }
            is LoginViewEvent.ConsumeError -> {
                _state.value = _state.value.copy(errors = _state.value.errors.filterNot { it  == _state.value.error }, error = "")

                //Reset Current Error if one exists
                if (_state.value.errors.isNotEmpty()) { _state.value = _state.value.copy(error = _state.value.errors[0]) }
            }
        }
    }
}