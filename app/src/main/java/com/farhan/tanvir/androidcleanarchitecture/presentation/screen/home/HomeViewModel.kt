package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.farhan.tanvir.domain.model.User
import com.farhan.tanvir.domain.useCase.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
) : ViewModel() {

    val getAllUsers = userUseCases.getAllUsersUseCase()
    val usersWithReservations = userUseCases.getAllUsersWithReservationUseCase()
    val usersWithoutReservations = userUseCases.getAllUsersWithoutReservationUseCase()

    val getSelectedUsersFlow = userUseCases.getAllSelectedUsersUseCase()

    private val _uiState = MutableStateFlow<UserListUiState>(UserListUiState.Success(false))
    val uiState: StateFlow<UserListUiState> = _uiState

    init {
        viewModelScope.launch (
            Dispatchers.Default, CoroutineStart.DEFAULT
        ) {
            getSelectedUsersFlow
                .catch { exception ->  _uiState.value = UserListUiState.Error(exception) }
                .collect { users -> _uiState.value = UserListUiState.Success(enabled = users.isNotEmpty()) }
        }
    }

    fun updateSelected(selected: Boolean, user: User){
        viewModelScope.launch (
            Dispatchers.IO, CoroutineStart.DEFAULT
        ) {


            if (selected) {
                userUseCases.selectUserUseCase(user)
            } else {
                userUseCases.unselectUserUseCase(user)
            }


        }
    }
}

sealed class UserListUiState {
    data class Success(val enabled: Boolean): UserListUiState()
    data class Error(val exception: Throwable): UserListUiState()
}