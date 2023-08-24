package com.tiphubapps.ax.rain.presentation.screen.details

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.*
import com.tiphubapps.ax.rain.Rain
import com.tiphubapps.ax.domain.repository.UserRepository
import com.tiphubapps.ax.domain.useCase.GetCurrentUserUseCase
import com.tiphubapps.ax.domain.useCase.UserUseCases
import com.google.gson.JsonObject
import com.tiphubapps.ax.domain.repository.AndroidFrameworkRepository
import com.tiphubapps.ax.domain.repository.AppError
import com.tiphubapps.ax.domain.repository.Result
import com.tiphubapps.ax.rain.R
import com.tiphubapps.ax.rain.presentation.helper.performVibration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import com.tiphubapps.ax.rain.util.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import java.util.Locale
import kotlin.math.absoluteValue

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager,
    private val application: Application,
) : AndroidViewModel(application) {

    init {
       println("TIME123 LoginViewModel Start")

        //val currentUserToken = MutableStateFlow<String>(userUseCases.getCurrentUserToken

        //Initialize token from DB to check for loginUiState?
        val savedToken = sessionManager.getEncryptedPreferencesValue("userToken")
        println("Saved TOken: ${savedToken}")
    }

    ////////Android Framework (Espresso Instrumented?)///////////////
    //Add haptic interactions on Composable states instead of calling them here
    //Will remove Android Framework calls from ViewModel
    fun handleError(error: AppError){
        //Move this to composable, on viewing/animating new error (and short one on swipe out?)
        //Then this method can be removed entirely
        performVibration(context = application.applicationContext)
        updateErrorMessage(error)
    }
    //Pretty sure this should be in the Application at the very least
    //Would rather it be part of Compose error handling, or other message handling from Compose side
    private fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        application.applicationContext.let {
            Toast.makeText(it, message, duration).show()
        }
    }
    //////////////////////////////////////////////////////

    //TODO: implement injection of this
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val errorMessage = throwable.message ?: "An error occurred"
        showToast(errorMessage)
    }

    ///////////////////////////////////////////////////////////////


    //CONVERT TO FLOW
    //ON COLLECT IF STATE IS LOGIN.SUCCESS -> navigateToHOme
    // Expose the local value as a Flow to the UI
    val localValueFlow: StateFlow<String> = userRepository.getLocalValueFlow()

    /////TODO: Finalize token handling...1///
    //Different Between Auth Token (API), Encrypted Token(WEBSOCKET), CurrentUserToken(USER_ID)???
    //private val _currentToken = MutableStateFlow<String>((sessionManager.getEncryptedPreferencesValue("userToken")) as String)
    //val currentToken : StateFlow<String> = _currentToken
    //val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjYzMWJhZWM3OTA0ZDQ3ZmExMzQ4YzgyZCIsInVzZXJuYW1lIjoiMTIxMzU1NTEyMTIiLCJleHBpcmUiOjE2ODI3NDQ5MDQ5Mzh9.WAnFXtzPFeWsff6iXv_zUF5CBZhdadbSzNcjgtRCLk0";
    ////////////////////////////////////////


    ///////////////////////////////////////////////////////////
    //Event ViewModel-Model State -- Mocked Data Tests?
    //Should be done in DOMAIN module?
    //Separate lower/inner logic to test?
    fun postLogin(username: String, password:String) {
        viewModelScope.launch (
            Dispatchers.IO, CoroutineStart.DEFAULT
        ) {
                //Show Loading
                _state.value = _state.value.copy(isLoading = true)

                //Remove Loading, Display Error or Success
                when (val response: Result<JsonObject> = userUseCases.useCaseLogin(username, password)) {

                       is Result.Success -> (response.data).get("data").let {

                           ////TODO: Finalize token handling...2///// SAVE IN A USECASE ///////
                           //(application as Rain).currentUserToken = it.asString;
                           //TODO: Finalize token handling 3/////// //////////
                           sessionManager.saveAuthToken(it.asString)
                           sessionManager.setEncryptedPreferences("userToken", it.asString)

                           Log.d("TIME123", "LoginViewModel saved token here: ${it.asString}")
                           /////////////////////////////////////////////////


                           //TODO: Convert to flow of userRepository (token/loggedInUser/getCurrentUser)
                           //i.e. if there is a logged in token go to home page automatically
                           //Or not? Set HOME on init[] if sharedPrefs, and set HOME on Login Success AFTER saving to sharedPrefs
                           _state.value = _state.value.copy(viewState = LoginUiState.Home)


                       }
                       is Result.Error -> { handleError(response.error) }
                }


                _state.value = _state.value.copy(isLoading = false)


                //TODO: Convert to class-level use case / flow state
               // println("current user token = " + userRepository.getCurrentToken())
                //if (userRepository.getCurrentToken() != null) {
                //    _state.value = _state.value.copy(viewState = LoginUiState.Home)
                //}
        }
    }

    fun postSignup(username:String, password:String){
        println("SIGNING UP WITH ${username} and ${password}")
    }

    fun postForgot(username:String){
        println("FORGOT ACCOUNT WITH ${username}")
    }
    /////////////////////////////////////////////////////////////////

    ////////ViewState Changes (UI tests)//////////////////
    fun showLogin(){
        _state.value = _state.value.copy(viewState = LoginUiState.Login)
    }
    fun showSignup(){
        _state.value = _state.value.copy(viewState = LoginUiState.Signup)
    }
    fun showForgot(){
        _state.value = _state.value.copy(viewState = LoginUiState.Forgot)
    }
    ////////////////////////////////////////////////////////


    //Should remove these? Extraneous UiState or relevant for other operations?
    //Activity View State, which is different from viewModel ViewState
    sealed class LoginUiState {
        object Default: LoginUiState()
        object Home: LoginUiState()
        object Login: LoginUiState()
        object Signup: LoginUiState()
        object Forgot: LoginUiState()
        data class Error(val exception: Throwable): LoginUiState()
    }


/////////////////////////////////////////////////////////////////////////




    ////////FULL STATE-EVENT LIFECYCLE/////////


    data class LoginViewState(
        val name: String = "12135551212",
        val email: String = "",
        val password: String = "admin",
        val isLoading: Boolean = false,
        val error: String = "",
        val errors: List<String> = emptyList(),
        val viewState: LoginUiState = LoginUiState.Default
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

    /////////////////////Event View-ViewModel State Changes/////////////////////////
    //JUnit ViewModel tests
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

            else -> {}
        }
    }

    internal fun updateErrorMessage(error: AppError){

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



}