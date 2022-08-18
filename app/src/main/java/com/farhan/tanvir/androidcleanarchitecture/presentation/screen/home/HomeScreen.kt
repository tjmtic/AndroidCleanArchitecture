package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home

import com.farhan.tanvir.androidcleanarchitecture.R

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppContentColor
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppThemeColor
import com.farhan.tanvir.domain.model.User
import com.google.accompanist.systemuicontroller.rememberSystemUiController

import androidx.compose.ui.res.stringResource
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.InfoComponent

@Composable
fun HomeScreen(onConfirm: () -> Unit,
               onConflict: () -> Unit,
               viewModel: HomeViewModel = hiltViewModel()
) {

    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colors.AppThemeColor

    val usersWithReservations = viewModel.usersWithReservations.collectAsLazyPagingItems()
    val usersWithoutReservations = viewModel.usersWithoutReservations.collectAsLazyPagingItems()

    val uiState = viewModel.uiState.collectAsState()

    /*fun callOnConfirm(){
        onConfirm()
    }

    fun callOnConflict(){
        onConflict()
    }*/

    fun showPopup(){
        //Need Users with Reservation to finish
        println("Navigate Popup / NoReservation")

    }

    fun bottomButtonAction(){
        when(uiState.value){
            is HomeViewUiState.Success -> onConfirm()
            is HomeViewUiState.Mixed -> onConflict()
            is HomeViewUiState.NoReservation -> showPopup()
        }
    }

    fun getUserValue(selected: Boolean, user: User){
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
                is HomeViewUiState.Error, HomeViewUiState.Empty -> HomeBottomBar("Continue", { }, false)
            }
        }
    )
}

