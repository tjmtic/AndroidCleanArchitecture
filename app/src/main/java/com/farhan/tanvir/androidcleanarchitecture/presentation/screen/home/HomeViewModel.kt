package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.farhan.tanvir.androidcleanarchitecture.AndroidCleanArchitecture
import com.farhan.tanvir.androidcleanarchitecture.MainActivity
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

    private val _currentUser: MutableLiveData<User> = MutableLiveData(null)
    private val _selectedUser: MutableStateFlow<JsonObject?> = MutableStateFlow(JsonObject())
    val currentUser: MutableStateFlow<JsonObject?> = _selectedUser

    val token = (getApplication<Application>().applicationContext as AndroidCleanArchitecture).currentUserToken

    fun getCurrentUser() {
        viewModelScope.launch {
            _selectedUser.value = userUseCases.getCurrentUserUseCase()
            // navController.navigate(route = Screen.Home.route)
            Log.d("TIME123", "New current user:" + _selectedUser.value)

            //user id to set socket namespace
            //MainActivity.setSocketNamespace(userId)
        }
    }

}