package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farhan.tanvir.domain.model.User
import com.farhan.tanvir.domain.useCase.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {
    private val _selectedUser: MutableStateFlow<User?> = MutableStateFlow(null)
    val selectedUser: StateFlow<User?> = _selectedUser

    private val _selectedToken: MutableLiveData<JSONObject?> = MutableLiveData(null)


    fun getUserDetails(userID: Int) {
        viewModelScope.launch {
            userUseCases.getUsersFromDBUseCase.invoke(userID).collect {
                _selectedUser.value = it
            }
        }
    }

    fun postLogin(email:String, password:String) {
        viewModelScope.launch {
            _selectedToken.value = userUseCases.postLoginUseCase.invoke(email, password)
        }
    }
}