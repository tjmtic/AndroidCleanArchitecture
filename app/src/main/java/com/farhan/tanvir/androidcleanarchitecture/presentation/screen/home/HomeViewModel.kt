package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.farhan.tanvir.domain.model.User
import com.farhan.tanvir.domain.useCase.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
) : ViewModel() {

    //List of Users(reserved = 1) as PagingData
    val usersWithReservations = userUseCases.getAllUsersWithReservationUseCase()
    //List of Users(reserved = 0) as PagingData
    val usersWithoutReservations = userUseCases.getAllUsersWithoutReservationUseCase()
    //List of Users(selected = 1) as Flow
    private val selectedUsersFlow = userUseCases.getAllSelectedUsersUseCase()

    //State for Bottom Button
    private val _uiState = MutableStateFlow<HomeViewUiState>(HomeViewUiState.Empty)
    //private val _uiState2 = MutableStateFlow<UserListUiState>(UserListUiState.Success(false))
    val uiState: StateFlow<HomeViewUiState> = _uiState

    val selectedReservations = selectedUsersFlow
        //Check for Users WITH Reservation
        .map { users -> users.filter { it.reserved } }
        //Set appropriate status on errors
        .catch { exception ->  _uiState.value = HomeViewUiState.Error(exception) }

    val selectedNoReservations = selectedUsersFlow
        //Check for Users WITHOUT Reservation
        .map { users -> users.filter { !it.reserved } }
        //Set appropriate status on errors
        .catch { exception ->  _uiState.value = HomeViewUiState.Error(exception) }

    //Initialize Collection on IO Threads
    init {
        viewModelScope.launch (
            Dispatchers.IO, CoroutineStart.DEFAULT
        ) {
            combine(selectedReservations, selectedNoReservations){usersWithReservation, usersWithoutReservation -> {
                Pair(
                    usersWithReservation.isNotEmpty(),// -> UserListUiState.Success,
                    usersWithoutReservation.isNotEmpty()// -> UserListUiState.Success
                )
            } }.collect {
                when (it()) {
                    Pair(true, true) -> _uiState.value = HomeViewUiState.Mixed
                    Pair(true, false) -> _uiState.value = HomeViewUiState.Success
                    Pair(false, true) -> _uiState.value = HomeViewUiState.NoReservation
                    Pair(false, false) -> _uiState.value = HomeViewUiState.Empty
                }
            }
        }
    }

    fun updateSelected(selected: Boolean, user: User){
        //Database(/Network) Operations on IO Threads
        viewModelScope.launch (
            Dispatchers.IO, CoroutineStart.DEFAULT
        ) {
            //Decoupled ACTION (Select/Unselect) and DATA (User.selected)
            if (selected) {
                userUseCases.selectUserUseCase(user)
            } else {
                userUseCases.unselectUserUseCase(user)
            }

        }
    }
}

//UiState resulting from Selected Users Collection
/*sealed class UserListUiState {
    object Success: UserListUiState()
    data class Error(val exception: Throwable): UserListUiState()
}*/

sealed class HomeViewUiState {
    object Success: HomeViewUiState()
    object Mixed: HomeViewUiState()
    object NoReservation: HomeViewUiState()
    object Empty: HomeViewUiState()
    data class Error(val exception: Throwable): HomeViewUiState()
}