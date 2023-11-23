package com.tiphubapps.ax.Rain.presentation.screen.details

import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tiphubapps.ax.data.util.CoroutineContextProvider
import com.tiphubapps.ax.domain.repository.UseCaseResult
import com.tiphubapps.ax.domain.useCase.LoginUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import com.tiphubapps.ax.Rain.presentation.delegate.AuthorizationDelegate
import com.tiphubapps.ax.Rain.presentation.delegate.AuthorizationDelegate2
import com.tiphubapps.ax.Rain.presentation.delegate.AuthorizationDelegate2Impl
import com.tiphubapps.ax.Rain.presentation.delegate.AuthorizationDelegateImpl
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.update

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userUseCases: LoginUseCases,
    //TODO: Can this be moved to the Impl declaration?
    // Yes.
    //authUseCases: AuthUseCases,
    authorizationDelegateImpl: AuthorizationDelegateImpl,
    authorizationDelegate2Impl: AuthorizationDelegate2Impl,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel(), AuthorizationDelegate by authorizationDelegateImpl, AuthorizationDelegate2 by authorizationDelegate2Impl {

    /////////////
    private lateinit var auth: FirebaseAuth
    /////////////

    override var authState = authorizationDelegateImpl.authState

    //TODO: implement injection (and use!) of this?
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val errorMessage = throwable.message ?: "An error occurred"
        //showToast(errorMessage)
    }

    ///////// ViewModel State setup ///////////
    //(OPEN) slug value
    private val _state = MutableStateFlow(LoginState())
    val state : StateFlow<LoginState> = _state

    //Probably not necessary anymore here
    private val _localValueFlow = MutableStateFlow("")
    val localValueFlow: StateFlow<String> = _localValueFlow

    var loginJob : Job?

    //EVENT BUS for View CHANGES
    //singup, login, forgot, isLoading?

    ///////////////////////////////////////////

    init {
        println("TIME123 LoginViewModel Init Start")
        auth = Firebase.auth
        val user = auth.currentUser
        println("TIME123 LoginViewModel User: ${user?.email}")

        //(PIPE) Expose/Stream values as Flows
        //TODO: REMOVE THIS
        // left as app architecture blueprint
        loginJob = viewModelScope.launch (
            coroutineContextProvider.io + coroutineExceptionHandler, CoroutineStart.DEFAULT
        ) {
                when(val value = userUseCases.useCaseAuthGetToken?.let { it() }){
                    is UseCaseResult.UseCaseSuccess -> {
                        _localValueFlow.value = value.data
                    }
                    is UseCaseResult.UseCaseError -> { value.exception.message?.let{
                        //TODO: Probably don't need to create banner errors for this
                        onEvent(LoginViewEvent.CreateError(it))
                    } ?: run{ onEvent(LoginViewEvent.CreateError("Unknown Error")) } }
                    else -> {
                        onEvent(LoginViewEvent.CreateError("Unknown Error "))
                    }
                }
            }
            /////////////////////////////////////////////////////////////////////////////////
        //}
        println("TIME123 LoginViewModel Init End")
    }

    override fun onCleared() {
        //TODO: How to test?
        viewModelScope.cancel()

        loginJob?.cancel()
    }

    /////////////////////View Events  --- View-ViewModel State Changes/////////////////////////
    fun onEvent(event: LoginViewEvent) {
        when (event) {
            is LoginViewEvent.ViewChanged -> {
                _state.update{ it.copy(viewState = event.viewState)}
            }
            is LoginViewEvent.LoadingChanged -> {
                _state.value = _state.value.copy(isLoading = event.isLoading)
            }
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
                _state.value.run { postLogin(this.email, this.password) }
                //postLogin(_state.value.email, _state.value.password)
            }
            is LoginViewEvent.SignupClicked -> {
                postSignup(_state.value.email, _state.value.password)
            }
            is LoginViewEvent.ForgotClicked -> {
                postForgot(_state.value.email)
            }
            is LoginViewEvent.CreateError -> {
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
        loginJob?.cancel()
        loginJob = viewModelScope.launch (
            coroutineContextProvider.io, CoroutineStart.DEFAULT
        ) {

                //TODO: Can this be done when response = Result.Loading?
                // -- currently there is no Result.Loading...
                // There IS NOW! This SHOULD be happening Somehow....
                // .
                // YES -- So. Would have to convert the login call to a Flow<UseCaseResult>... or something like that
                // initially, it will emit(STATUS.LOADING), with an api call
                // followed by emit(STATUS.SUCCESS(data)).
                //Show Loading
                onEvent(LoginViewEvent.LoadingChanged(true))

                /////////////////////TOLEARN///////////////////////////
                //TODO: Should pass SessionManager with call?
                // It would implicitly save token, and only need to display on error?
                // NO -- do not put higher level module as dependency in lower level module.
                // -- IF sessionManager was a module in DOMAIN (or just lower, outside of app)
                // it would be acceptable? UTIL-type module (Feature-Module Architecture).
                // STILL NO -- sharedPreferences is part of Android Framework (is it?). IT DOES NOT GO TO ANY LOWER LEVELS.
                //
                // Could be defined a separate dependency in DATA / Persistence layer. Probably, it should not
                // be referenced by the viewModel except through a useCase.
                ///////////////////////////////////////////////////////

                when (val loginResult: UseCaseResult<String> = userUseCases.useCaseLogin(username, password)) {
                        //Display Error or Success
                       is UseCaseResult.UseCaseSuccess -> {
                           println("Success --- Login")
                           //continueFromNavigationDeeplink()
                       }
                       is UseCaseResult.UseCaseError -> {
                           loginResult.exception.message?.let {
                               onEvent(LoginViewEvent.CreateError(it))
                           } ?: run { onEvent(LoginViewEvent.CreateError("Unknown Error ")) }
                       }
                       else -> {
                            //TODO: This should never actually happen right?
                            // Loading is a placeholder for other async/await operations, but not used here?
                            // Edge case here being if it does come back as LOADING it will not change.
                            // Need to check, stop, and re-start?
                            println("Loading --- Login")
                            //TODO: NO. Should be useful for stateful responses, emit(LOADING) and emit(SUCCESS/ERROR)
                            // ... not going to emit that from the backend though. Still UI State Declaration. Relevant
                            // datastreams will emit it as they begin, and resolve to be the result.
                            // _localValueFlow here would emit a LOADING, before the useCase invocation.
                        }
                }
                //Hide loading
                onEvent(LoginViewEvent.LoadingChanged(false))
        }


        auth.signInWithEmailAndPassword(username, password).addOnCompleteListener {
                task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                println("Signed In FIREBASE USER!!!!!")
                val user = auth.currentUser
                println(user?.email)
            } else {
                // If sign in fails, display a message to the user.
                println("DID not Create FIrebase User. 111111111")
            }

            if (!task.isSuccessful) {
                println("DID not Create FIrebase User. 2222222222 ")
            }
        }

    }

    fun postSignup(username:String, password:String){
        //TODO: Implement this call
        println("SIGNING UP WITH ${username} and ${password}")

        //same structure as login call. on success, move to verify screen
        //will probably need to add this check and response path to login as well.


        auth.createUserWithEmailAndPassword(username, password).addOnCompleteListener {
                task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                println("Created FIREBASE USER!!!!!")
            } else {
                // If sign in fails, display a message to the user.
                println("DID not Create FIrebase User. 111111111")
            }

            if (!task.isSuccessful) {
                println("DID not Create FIrebase User. 2222222222 ")
            }
        }

    }

    fun postForgot(username:String){
        //TODO: Implement this call
        println("FORGOT ACCOUNT WITH ${username}")
        //on success, show message, move back to login screen
    }
    /////////////////////////////////////////////////////////////////

    ////////ViewState Changes (UI tests)//////////////////
    fun showLogin(){
        onEvent(LoginViewEvent.ViewChanged(LoginUiState.Login))
        //_state.value = _state.value.copy(viewState = LoginUiState.Login)
    }
    fun showSignup(){
        onEvent(LoginViewEvent.ViewChanged(LoginUiState.Signup))
        //_state.value = _state.value.copy(viewState = LoginUiState.Signup)
    }
    fun showForgot(){
        onEvent(LoginViewEvent.ViewChanged(LoginUiState.Forgot))
        //_state.value = _state.value.copy(viewState = LoginUiState.Forgot)
    }
    ////////////////////////////////////////////////////////


    //ViewModel State Classes
    data class LoginState(
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
        //GENERIC VIEWMODEL
        data class ViewChanged(val viewState: LoginUiState) : LoginViewEvent()
        data class LoadingChanged(val isLoading: Boolean) : LoginViewEvent()

        //LOGIN SPECIFIC
        //Input Values
        data class EmailChanged(val email: String) : LoginViewEvent()
        data class PasswordChanged(val password: String) : LoginViewEvent()
        data class NameChanged(val name: String) : LoginViewEvent()
        //User Actions
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