package com.tiphubapps.ax.rain.presentation.screen.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.tiphubapps.ax.rain.Rain
import com.tiphubapps.ax.domain.useCase.UserUseCases
import com.google.gson.JsonObject
import com.tiphubapps.ax.domain.repository.UseCaseResult
import com.tiphubapps.ax.rain.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val sessionManager: SessionManager,
    application: Application
) : AndroidViewModel(application) {
    //private val _selectedUser: MutableStateFlow<User?> = MutableStateFlow(null)
    private val _selectedUser: MutableStateFlow<JsonObject?> = MutableStateFlow(JsonObject())

    val selectedUser: StateFlow<JsonObject?> = _selectedUser

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Default)
    val uiState: StateFlow<LoginUiState> = _uiState

    private val _currentToken = MutableStateFlow<String>((sessionManager.getEncryptedPreferencesValue("userToken")) as String)
    val token : StateFlow<String> = _currentToken


    init {
        Log.d("TIME123","initializeing profileVIEWMODEL....");
        viewModelScope.launch {
            token.let {
               // _selectedUser.value = userUseCases.getCurrentUserWithTokenUseCase(it.value)
                val respo = userUseCases.getCurrentUserWithTokenUseCase(it.value)

                when(respo){
                    is UseCaseResult.UseCaseSuccess -> _selectedUser.value = JsonObject().apply{ addProperty("user", Gson().toJson(respo.data)) }
                    else -> {}
                }

                // navController.navigate(route = Screen.Home.route)
                Log.d("TIME123", "New current user:" + _selectedUser.value)

                //user id to set socket namespace
                //MainActivity.setSocketNamespace(userId)
                _selectedUser.value?.get("token")?.let {
                    _uiState.value = LoginUiState.Valid
                    //(getApplication<Application>().applicationContext as AndroidCleanArchitecture).currentUserSocketId =
                    //    it.asString;
                } ?: run {
                    //_uiState.value = LoginUiState.Invalid
                    println("Selected User Hasw no token, woul dbe loggin out....")
                }
            }
        }
    }

    fun getUserDetails(userID: Int) {
        viewModelScope.launch {
            userUseCases.getUsersFromDBUseCase.invoke(userID = userID).collect {
                //_selectedUser.value = it
            }
        }
    }

    fun logout(){
        sessionManager.clear()
        (getApplication<Application>().applicationContext as Rain).logout()
        _uiState.value = LoginUiState.Invalid

    }

    sealed class LoginUiState {
        object Default: LoginUiState()
        object Valid: LoginUiState()
        object Invalid: LoginUiState()
        data class Error(val exception: Throwable): LoginUiState()
    }
}