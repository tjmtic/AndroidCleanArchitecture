package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home

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

    val _isSelected = MutableLiveData<Boolean>(false);
    val isSelected: LiveData<Boolean> = _isSelected

    val _selectedUsers = MutableLiveData<List<User>>(emptyList());
    val selectedUsers: LiveData<List<User>> = _selectedUsers;

    fun updateSelected(sel: Boolean){
        _isSelected.postValue(sel);
    }

    fun updateSelected(user: User){
        println("Updating Selected:" + user)
        selectedUsers.value?.let {
                if (it.contains(user)) {
                    val newList = it.toMutableList()
                    newList.remove(user)
                    _selectedUsers.postValue(newList)
                }
            else {
                val newList = it.toMutableList()
                newList.add(user)
                _selectedUsers.postValue(newList)
            }
            }
    }

}