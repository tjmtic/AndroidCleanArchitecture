package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.details

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppContentColor
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppThemeColor


@Composable
fun UserDetailsScreen(
    userId: String,
    navController: NavController,
    viewModel: UserDetailsViewModel = hiltViewModel(),
) {
    viewModel.getUserDetails(userID = userId.toInt())
    val userDetails by viewModel.selectedUser.collectAsState()
    Scaffold(
        topBar={
               UserDetailsTopBar(navController)
        },
        contentColor = MaterialTheme.colors.AppContentColor,
        backgroundColor = MaterialTheme.colors.AppThemeColor,
        content = {
            //userDetails?.let { UserDetailsContent(it) }
        })
}

