package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.farhan.tanvir.androidcleanarchitecture.presentation.navigation.Screen
import com.farhan.tanvir.domain.model.User
import com.farhan.tanvir.domain.useCase.UserUseCases
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    //val navController: NavHostController
) : ViewModel() {
    private val _selectedUser: MutableStateFlow<JsonObject?> = MutableStateFlow(null)
    val selectedUser: StateFlow<JsonObject?> = _selectedUser

    private val _selectedToken: MutableStateFlow<JsonObject?> = MutableStateFlow(null)
    val selectedToken: StateFlow<JsonObject?> = _selectedToken

    val finishedLogin : MutableStateFlow<Boolean> = MutableStateFlow(false)
    //combineAndCompute( selectedUser && selectedToken )

    fun getUserDetails(userID: Int) {
        viewModelScope.launch {
            userUseCases.getUsersFromDBUseCase.invoke(userID).collect {
               // _selectedUser.value = it
            }
        }
    }

    fun postLogin(email:String, password:String) {
        viewModelScope.launch {
            _selectedToken.value = userUseCases.postLoginUseCase.invoke(email, password)
           // navController.navigate(route = Screen.Home.route)

            getCurrentUser()
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            _selectedUser.value = userUseCases.getCurrentUserUseCase()
            // navController.navigate(route = Screen.Home.route)
            Log.d("TIME123", "New current user:" + _selectedUser.value)
        }
    }
}