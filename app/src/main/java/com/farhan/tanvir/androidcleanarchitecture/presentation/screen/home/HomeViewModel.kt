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

    //State for Combined States
    private val _uiState = MutableStateFlow<HomeViewUiState>(HomeViewUiState.Empty)
    val uiState: StateFlow<HomeViewUiState> = _uiState

    //Flow of selected Users with reservation
    val selectedWithReservations = selectedUsersFlow
        //Check for Users WITH Reservation
        .map { users -> users.filter { it.reserved } }
        //Set appropriate status on error
        .catch { exception ->  _uiState.value = HomeViewUiState.Error(exception) }

    //Flow of selected Users without Reservation
    val selectedWithoutReservations = selectedUsersFlow
        //Check for Users WITHOUT Reservation
        .map { users -> users.filter { !it.reserved } }
        .catch { exception ->  _uiState.value = HomeViewUiState.Error(exception) }

    //Initialize Collection on IO Threads
    init {
        viewModelScope.launch (
            Dispatchers.IO, CoroutineStart.DEFAULT
        ) {
            //Unify related flow collection actions
            combine(selectedWithReservations, selectedWithoutReservations) {
                    //Simple data transform / validation
                    usersWithReservations, usersWithoutReservations ->
                        CombinedFlowStates(
                          usersWithReservations = if (usersWithReservations.isNotEmpty()) UserListUiState.Valid else UserListUiState.Invalid,
                          usersWithoutReservations = if (usersWithoutReservations.isNotEmpty()) UserListUiState.Valid else UserListUiState.Invalid
                        )
                }
                .collect {
                    //Set appropriate UiState
                    when (it) {
                        CombinedFlowStates(UserListUiState.Valid, UserListUiState.Valid) -> _uiState.value = HomeViewUiState.Mixed
                        CombinedFlowStates(UserListUiState.Valid, UserListUiState.Invalid) -> _uiState.value = HomeViewUiState.Success
                        CombinedFlowStates(UserListUiState.Invalid, UserListUiState.Valid) -> _uiState.value = HomeViewUiState.NoReservation
                        CombinedFlowStates(UserListUiState.Invalid, UserListUiState.Invalid) -> _uiState.value = HomeViewUiState.Empty
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

//UiState representing final state
sealed class HomeViewUiState {
    object Success: HomeViewUiState()
    object Mixed: HomeViewUiState()
    object NoReservation: HomeViewUiState()
    object Empty: HomeViewUiState()
    data class Error(val exception: Throwable): HomeViewUiState()
}

//UiState resulting from selected users collection
sealed class UserListUiState {
    object Valid: UserListUiState()
    object Invalid: UserListUiState()
}

//Data holder for flow states
data class CombinedFlowStates(val usersWithReservations: UserListUiState, val usersWithoutReservations:UserListUiState)
