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
import com.tiphubapps.ax.data.util.CoroutineContextProvider
import com.tiphubapps.ax.domain.repository.AndroidFrameworkRepository
import com.tiphubapps.ax.domain.repository.AppError
import com.tiphubapps.ax.domain.repository.UseCaseResult
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
    //private val application: Application,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel() {

    init {
       println("TIME123 LoginViewModel Start")
        //Initialize token from DB to check for loginUiState?
        //TODO: SHOULD DO THIS IN MAINACTIVITY?
        sessionManager.getEncryptedPreferencesValue("userToken")?.let {
            userRepository.updateLocalValue(it)
        }
    }

    //TODO: implement injection (and use!) of this?
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val errorMessage = throwable.message ?: "An error occurred"
        //showToast(errorMessage)
    }

    ///////// ViewModel State setup ///////////
    //(OPEN) slug value
    private val _state = MutableStateFlow(LoginViewState())
    val state : StateFlow<LoginViewState> = _state

    //(PIPE) Expose/Stream values as Flows
    val localValueFlow: StateFlow<String> = userRepository.getLocalValueFlow()

    //(MIX) Transform Data as appropriate
    // ...

    ///////////////////////////////////////////

    /////////////////////View Events  --- View-ViewModel State Changes/////////////////////////
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
            is LoginViewEvent.CreateError -> {
               // _state.value = _state.value.copy(errors = _state.value.errors.plus { it  == _state.value.error }, error = "")
                //TODO: This implementation is backwards.
                // The method should call the event. (Or vice versa)
                // But remove other references this method outside of this event call.
                // Send EVENT in those places instead?
                // -- updateErrorMessage is a STATE changing method
                // STATE changes MUST be invoked by an EVENT. --
                updateErrorMessage(event.message)
            }
            is LoginViewEvent.ConsumeError -> {
                _state.value = _state.value.copy(errors = _state.value.errors.filterNot { it  == _state.value.error }, error = "")

                //Reset Current Error if one exists
                if (_state.value.errors.isNotEmpty()) { _state.value = _state.value.copy(error = _state.value.errors[0]) }
            }

            else -> { println("Unknown Event Called") }
        }
    }
    internal fun updateErrorMessage(error: String){
        val errorMessage = when(error){
            "Service Issue."  -> {
                "Service Issue."
            }
            "Input Error."  -> {
                "Input Error."
            }
            "Network Error." -> {
                "Network Error"
            }
            else -> { "Unknown Error" }
        }
        _state.value = _state.value.copy(error = errorMessage, errors = _state.value.errors.plus(errorMessage))
    }
    ////////////////////////////

    /////////////////////////////COROUTINES//////////////////////////////
    //Model Events ---  ViewModel-Model State -- Mocked Data Tests?
    fun postLogin(username: String, password:String) {
        viewModelScope.launch (
            coroutineContextProvider.io, CoroutineStart.DEFAULT
        ) {
                //TODO: Can this be done when response = Result.Loading?
                // -- currently there is no Result.Loading...
                // There IS NOW! This SHOULD be happening Somehow....
                //Show Loading
                _state.value = _state.value.copy(isLoading = true)

                /////////////////////TOLEARN///////////////////////////
                //TODO: Should pass SessionManager with call?
                // It would implicitly save token, and only need to display on error?
                // NO -- do not put higher level module as dependency in lower level module.
                // -- IF sessionManager was a module in DOMAIN (or just lower, outside of app)
                // it would be acceptable? UTIL-type module (Feature-Module Architecture).
                // STILL NO -- sharedPreferences is part of Android Framework (is it?). IT DOES NOT GO TO ANY LOWER LEVELS.
                ///////////////////////////////////////////////////////

                when (val loginResult: UseCaseResult<JsonObject> = userUseCases.useCaseLogin(username, password)) {
                        //Display Error or Success
                        //TODO: How to decouple this field from result?
                        // "data" v. "token" field name
                        // NEITHER -- UseCaseResult<String>
                        // -- in future, UseCaseResult<LoginResponse>? from <LoginRequest>
                        // POSSIBLY -- On DATA layer, with <LoginRequest>. Here we are only interested in the result value,
                        // and stay decoupled from the actual request implementation.
                       is UseCaseResult.UseCaseSuccess -> { (loginResult.data).get("data").let {
                           sessionManager.setUserToken(it.asString) } }
                       is UseCaseResult.UseCaseError -> {
                           loginResult.exception.message?.let { updateErrorMessage(it) }
                       }
                        else -> {
                            //TODO: This should never actually happen right?
                            // Loading is a placeholder for other async/await operations, but not used here?
                            // Edge case here being if it does come back as LOADING it will not change.
                            // Need to check and re-start?
                            println("Loading --- Login")
                        }
                }
                //Hide loading
                _state.value = _state.value.copy(isLoading = false)
        }
    }

    fun postSignup(username:String, password:String){
        //TODO: Implement this call
        println("SIGNING UP WITH ${username} and ${password}")

        //same structure as login call. on success, move to verify screen
        //will probably need to add this check and response path to login as well.
    }

    fun postForgot(username:String){
        //TODO: Implement this call
        println("FORGOT ACCOUNT WITH ${username}")
        //on success, show message, move back to login screen
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


    //ViewModel State Classes
    data class LoginViewState(
        val name: String = "",
        val email: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
        val error: String = "",
        val errors: List<String> = emptyList(),
        val viewState: LoginUiState = LoginUiState.Default
    )

    sealed class LoginUiState {
        object Default: LoginUiState()
        object Home: LoginUiState()
        object Login: LoginUiState()
        object Signup: LoginUiState()
        object Forgot: LoginUiState()
        data class Error(val exception: Throwable): LoginUiState()
    }


    //ViewModel Events
    sealed class LoginViewEvent {
        data class EmailChanged(val email: String) : LoginViewEvent()
        data class PasswordChanged(val password: String) : LoginViewEvent()
        data class NameChanged(val name: String) : LoginViewEvent()
        object LoginClicked : LoginViewEvent()
        object SignupClicked : LoginViewEvent()
        object ForgotClicked : LoginViewEvent()
        /////////////////////////////////////////////////////////////////////////
        //TODO: This is an opportunity for a presenter-ui architecture.
        // errorViewModel (or messageViewModel) and UI components (i.e. different types of messages)
        // Injected and wrapped-ui
        data class CreateError(val message: String) : LoginViewEvent()
        object ConsumeError : LoginViewEvent()
        /////////////////////////////////////////////////////////////////////////
    }



}