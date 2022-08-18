package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home

import com.farhan.tanvir.androidcleanarchitecture.R

import android.util.Log
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Path.Companion.combine
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.farhan.tanvir.androidcleanarchitecture.presentation.navigation.Screen
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppContentColor
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppThemeColor
import com.farhan.tanvir.domain.model.User
import com.farhan.tanvir.domain.model.UserList
import com.google.accompanist.systemuicontroller.rememberSystemUiController

import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LifecycleOwner
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.InfoComponent
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {

    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colors.AppThemeColor

    val usersWithReservations = viewModel.usersWithReservations.collectAsLazyPagingItems()
    val usersWithoutReservations = viewModel.usersWithoutReservations.collectAsLazyPagingItems()

    val uiState = viewModel.uiState.collectAsState()

    fun navigateConfirm(){
        //Navigate to Confirm Page
    }

    fun navigateConflict(){
        //Navigate to Conflict Page
    }

    fun showPopup(){
        //Need Users with Reservation to finish
    }

    fun bottomButtonAction(){
        when(uiState.value){
            is HomeViewUiState.Success, -> navigateConfirm()
            is HomeViewUiState.Mixed -> navigateConflict()
            is HomeViewUiState.NoReservation -> showPopup()
        }
    }

    fun getUserValue(selected: Boolean, user : User){
        viewModel.updateSelected(selected, user)
    }


    SideEffect {
        systemUiController.setStatusBarColor(
            color = systemBarColor
        )
    }

    Scaffold(
        backgroundColor = MaterialTheme.colors.AppThemeColor,
        contentColor = MaterialTheme.colors.AppContentColor,
        topBar = {
            HomeTopBar()
        },
        content = {
            UserListContent(usersWithReservations = usersWithReservations,
                            usersWithoutReservations = usersWithoutReservations,
                            getUserValue = {selected, user -> getUserValue(selected, user)},)
            InfoComponent(info = stringResource(id = R.string.info) )
        },
        bottomBar = {
            when(uiState.value){
                is HomeViewUiState.Success, HomeViewUiState.Mixed, HomeViewUiState.NoReservation -> HomeBottomBar("Continue", { bottomButtonAction() }, true)
                //is HomeViewUiState.Mixed -> HomeBottomBar("Continue", { }, true)
                //is HomeViewUiState.NoReservation -> HomeBottomBar("Continue", { }, true)
                //is HomeViewUiState.Empty -> HomeBottomBar("Continue", { }, false)
                is HomeViewUiState.Error, HomeViewUiState.Empty -> HomeBottomBar("Continue", { }, false)
               // else -> HomeBottomBar("Continue", { showPopup() }, false)
            }
        }
    )
}

