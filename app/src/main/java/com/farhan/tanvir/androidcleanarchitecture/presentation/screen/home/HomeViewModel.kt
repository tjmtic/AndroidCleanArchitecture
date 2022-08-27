package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.farhan.tanvir.androidcleanarchitecture.AndroidCleanArchitecture
import com.farhan.tanvir.androidcleanarchitecture.MainActivity
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.details.LoginViewModel
import com.farhan.tanvir.androidcleanarchitecture.util.SocketHandler
import com.farhan.tanvir.domain.model.User
import com.farhan.tanvir.domain.useCase.UserUseCases
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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

    private val _currentUser: MutableLiveData<User> = MutableLiveData(null)
    private val _selectedUser: MutableStateFlow<JsonObject?> = MutableStateFlow(JsonObject())
    val currentUser: MutableStateFlow<JsonObject?> = _selectedUser



    val token = (getApplication<Application>().applicationContext as AndroidCleanArchitecture).currentUserToken

    init {
        Log.d("TIME123","initializeing homewVIEWMODEL....");
        viewModelScope.launch {
            _selectedUser.value = userUseCases.getCurrentUserUseCase()
            _allUsers.value = userUseCases.getAllUsersUseCase()
            // navController.navigate(route = Screen.Home.route)
            Log.d("TIME123", "New current user:" + _selectedUser.value)

            //user id to set socket namespace
            //MainActivity.setSocketNamespace(userId)
            _selectedUser.value?.get("socketId")?.let {
                (getApplication<Application>().applicationContext as AndroidCleanArchitecture).currentUserSocketId =
                    it.asString;
            }
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            _selectedUser.value = userUseCases.getCurrentUserUseCase()
            // navController.navigate(route = Screen.Home.route)
            Log.d("TIME123", "New current user:" + _selectedUser.value)

            //user id to set socket namespace
            //MainActivity.setSocketNamespace(userId)
        }
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


    sealed class HomeUiState {
        object Default: HomeUiState()
        object Send: HomeUiState()
        object Receive: HomeUiState()
        data class Error(val exception: Throwable): HomeUiState()
    }

}