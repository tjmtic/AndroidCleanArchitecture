package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.*
import com.farhan.tanvir.androidcleanarchitecture.AndroidCleanArchitecture
import com.farhan.tanvir.androidcleanarchitecture.MainActivity
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.details.LoginViewModel
import com.farhan.tanvir.androidcleanarchitecture.util.SocketHandler
import com.farhan.tanvir.domain.model.User
import com.farhan.tanvir.domain.useCase.UserUseCases
import com.google.gson.JsonArray
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

    private val _uiStateCamera = MutableStateFlow<CameraUiState>(CameraUiState.Disabled)
    val uiStateCamera: StateFlow<CameraUiState> = _uiStateCamera

    //private val _currentUser: MutableLiveData<User> = MutableLiveData(null)
    private val _currentUser: MutableStateFlow<JsonObject?> = MutableStateFlow(JsonObject())
    val currentUser: MutableStateFlow<JsonObject?> = _currentUser

    private val _selectedUser: MutableStateFlow<JsonObject?> = MutableStateFlow(JsonObject())
    val selectedUser: MutableStateFlow<JsonObject?> = _selectedUser


    private val _currentToken = MutableStateFlow<String>(((application.applicationContext as AndroidCleanArchitecture).getEncryptedPreferencesValue("userToken")) as String)
    val token : StateFlow<String> = _currentToken
    //val token = (getApplication<Application>().applicationContext as AndroidCleanArchitecture).currentUserToken

    val qrImage: Bitmap? = token?.let{(getApplication<Application>().applicationContext as AndroidCleanArchitecture).generateQR(token.value)}

    init {
        Log.d("TIME123","initializeing homewVIEWMODEL....");
        viewModelScope.launch {
            token?.let {
                _currentUser.value = userUseCases.getCurrentUserWithTokenUseCase(token.value)
                _allUsers.value = userUseCases.getAllUsersWithTokenUseCase(token.value)
            }

            // navController.navigate(route = Screen.Home.route)
            Log.d("TIME123", "New current user:" + _currentUser.value)

            //user id to set socket namespace
            //MainActivity.setSocketNamespace(userId)
            _currentUser.value?.get("socketId")?.let {
                (getApplication<Application>().applicationContext as AndroidCleanArchitecture).currentUserSocketId =
                    it.asString;
            }
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

    fun showCamera(){
        _uiStateCamera.value = CameraUiState.Enabled
    }

    fun hideCamera(){
        _uiStateCamera.value = CameraUiState.Disabled
    }

    fun toggleCamera(){
        when(_uiStateCamera.value){
            is CameraUiState.Enabled -> _uiStateCamera.value = CameraUiState.Disabled
            is CameraUiState.Disabled -> _uiStateCamera.value = CameraUiState.Enabled
        }
    }

    fun setSelectedById(id: String){
        _allUsers.value?.get("receivers")?.let {
            println("USER ELEMENT ${it}")

            for (item in it as JsonArray) {
                println(" ${id} USER ARRAY ${item}")

                if((item as JsonObject).get("id").asString.equals(id)){
                    _selectedUser.value = item
                    println("SET SELECTED USER OBJECT ${item}")
                }
                else{
                    println((item as JsonObject).get("id").asString)
                    println(id)
                }
            }
        }
    }


    sealed class HomeUiState {
        object Default: HomeUiState()
        object Send: HomeUiState()
        object Receive: HomeUiState()
        data class Error(val exception: Throwable): HomeUiState()
    }

    sealed class CameraUiState {
        object Enabled: CameraUiState()
        object Disabled: CameraUiState()
        data class Error(val exception: Throwable): CameraUiState()
    }

}