package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.farhan.tanvir.androidcleanarchitecture.R

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppContentColor
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppThemeColor
import com.farhan.tanvir.domain.model.User
import com.google.accompanist.systemuicontroller.rememberSystemUiController

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.InfoComponent

@Composable
fun HomeScreen(onConfirm: () -> Unit,
               onConflict: () -> Unit,
               viewModel: HomeViewModel = hiltViewModel()
) {

    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colors.AppThemeColor
    //
    val allUsers = viewModel.allUsers.collectAsLazyPagingItems()
    //val usersWithReservations = viewModel.usersWithReservations.collectAsLazyPagingItems()
    //val usersWithoutReservations = viewModel.usersWithoutReservations.collectAsLazyPagingItems()
    //
    val uiState = viewModel.uiState.collectAsState()

    //Save display state in Composable Lifecycle
    val displayPopup = rememberSaveable { mutableStateOf(false) }
    fun togglePopup(){ displayPopup.value = !displayPopup.value }

    fun bottomButtonAction(){
        //Perform appropriate action
        when(uiState.value){
            is HomeViewUiState.Success -> onConfirm()
            is HomeViewUiState.Mixed -> onConflict()
            is HomeViewUiState.NoReservation -> togglePopup()
        }
    }

    //
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
            ConstraintLayout(
                //modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                val (list1, list2, text) = createRefs()
                //val listState = rememberLazyListState()

                Column(
                    modifier = Modifier
                        //.height(IntrinsicSize.Max)
                        .fillMaxWidth()
                        // .verticalScroll()
                        .constrainAs(list1) {
                            top.linkTo(parent.top, margin = 16.dp)
                        }
                    // verticalAlignment = Alignment.CenterVertically
                ) {
                    UserListContent(title = "These Guests Have Reservations",
                        users = allUsers,
                        getUserValue = { selected, user -> getUserValue(selected, user) })
                }
                /*Column(
                    modifier = Modifier
                        // .height(IntrinsicSize.Max)
                        .fillMaxWidth()
                        .constrainAs(list2) {
                            top.linkTo(list1.bottom, margin = 16.dp)
                        }//.//verticalAlignment = Alignment.CenterVertically
                ) {

                    UserListContent(title = "These Guests Need Reservations",
                        users = usersWithoutReservations,
                        getUserValue = { selected, user -> getUserValue(selected, user) })
                }*/
                Column(
                    modifier = Modifier
                        .height(IntrinsicSize.Max)
                        .fillMaxWidth()
                        .constrainAs(text) {
                            top.linkTo(list1.bottom, margin = 16.dp)
                        },
                ) {
                    InfoComponent(info = stringResource(id = R.string.info))
                }
            }
        },
        bottomBar = {
            when(uiState.value){
                is HomeViewUiState.Success,
                   HomeViewUiState.Mixed,
                   HomeViewUiState.NoReservation -> HomeBottomBar(text = "Continue",
                                                                  onClickButton = { bottomButtonAction() },
                                                                  enabled = true,
                                                                  onClickPopup = { togglePopup() },
                                                                  displayPopup = displayPopup.value)
                is HomeViewUiState.Error,
                   HomeViewUiState.Empty -> HomeBottomBar(text = "Continue")
            }
        }
    )
}

