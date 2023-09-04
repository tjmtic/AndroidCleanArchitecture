package com.tiphubapps.ax.rain.presentation.screen.details

import android.app.Application
import androidx.lifecycle.*
import com.tiphubapps.ax.rain.Rain
import com.tiphubapps.ax.domain.repository.UserRepository
import com.tiphubapps.ax.domain.useCase.GetCurrentUserUseCase
import com.tiphubapps.ax.domain.useCase.UserUseCases
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.tiphubapps.ax.rain.util.SessionManager
import javax.inject.Named

@HiltViewModel
class SignupViewModel @Inject constructor(
    @Named("suite") private val userUseCases: UserUseCases,
    private val userRepository: UserRepository,
    //val navController: NavHostController
    application: Application
) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application.applicationContext)

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Login)
    val uiState: StateFlow<LoginUiState> = _uiState
    private val _networkUiState = MutableStateFlow<NetworkUiState>(NetworkUiState.Neutral)



    private val _username = MutableStateFlow<String>("");
    val username: StateFlow<String> = _username
    private val _password = MutableStateFlow<String>("");
    val password: StateFlow<String> = _password


    fun updateUsername(update: String){ _username.value = update;}
    fun updatePassword(update: String){ _password.value = update;}


    //CONVERT TO FLOW
    //ON COLLECT IF STATE IS LOGIN.SUCCESS -> navigateToHOme

    private val _selectedUser: MutableStateFlow<JsonObject?> = MutableStateFlow(null)
    val selectedUser: StateFlow<JsonObject?> = _selectedUser

    private val _selectedToken = userRepository.getCurrentToken();//MutableStateFlow<String> = MutableStateFlow("")
    //val selectedToken: StateFlow<String> = _selectedToken

    val isLoggedIn : MutableStateFlow<Boolean> = MutableStateFlow(false)
    //combineAndCompute( selectedUser && selectedToken )

    fun getUserDetails(userID: Int) {
        viewModelScope.launch {
            userUseCases.getUsersFromDBUseCase!!.invoke(userID).collect {
               // _selectedUser.value = it
            }
        }
    }

    fun postLogin() {
        viewModelScope.launch (
            Dispatchers.IO, CoroutineStart.DEFAULT
        ){
            //Show Loading
            _networkUiState.value = NetworkUiState.Loading
            //Send Request
            ////TODO: Should make a UseCaseFactory , implement invoke() method calls for injection / hoisting
            userUseCases.postLoginUseCase!!.username = "2135551212"//username.value
            userUseCases.postLoginUseCase!!.password = "admin"//password.value

            //Should set value as current user token in user repository in use case
            //This object should hold the network response (success/err/err)
            val response: JsonObject? = userUseCases.postLoginUseCase!!.invoke()

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

            response?.get("token")?.let{
                // _selectedToken.value = it.asString;
                //println(_selectedToken.value)
                _networkUiState.value = NetworkUiState.Success

                (getApplication<Application>().applicationContext as Rain).currentUserToken = it.asString;
                //TODO: Convert to flow of userRepository (token/loggedInUser/getCurrentUser)
                _uiState.value = LoginUiState.Home

                sessionManager.saveAuthToken(it.asString)

            } ?: run {
               // _networkUiState.value = NetworkUiState.Failure(it.value)
                _networkUiState.value = NetworkUiState.Failure("Network Error")
            }

            println("current user token = " + userRepository.getCurrentToken())
            if(userRepository.getCurrentToken() != null){
                _uiState.value = LoginUiState.Home

               // navController.navigate(Screen.Home.route)
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
            withNetworkModal(userUseCases.getCurrentUserUseCase!!,
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
}