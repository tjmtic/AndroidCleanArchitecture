package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farhan.tanvir.domain.model.User
import com.farhan.tanvir.domain.useCase.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
) : ViewModel() {

    val getAllUsers = userUseCases.getAllUsersUseCase()
    val usersWithReservations = userUseCases.getAllUsersWithReservationUseCase()
    val usersWithoutReservations = userUseCases.getAllUsersWithoutReservationUseCase()

    val _selectedUsers = MutableLiveData<List<User>>(emptyList<User>());
    val selectedUsers: LiveData<List<User>> = _selectedUsers;

    var uiState by mutableStateOf(HomeViewModelUiState(false))
        private set


    fun updateSelected(selected: Boolean, user: User){
        println("Updating Selected:" + user + selected)
        _selectedUsers.value?.let {

            val newList = it.toMutableList()

            if (it.contains(user) && !selected) {
                    newList.remove(user)
                    uiState = HomeViewModelUiState(newList.isNotEmpty())
            }

            else if(!it.contains(user) && selected) {
                newList.add(user)
                uiState = HomeViewModelUiState(true)
            }

            _selectedUsers.postValue(newList)
         }
    }
}

data class HomeViewModelUiState(
    val enabled: Boolean = false
)