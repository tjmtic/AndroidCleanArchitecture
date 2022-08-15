package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.details

import android.util.Log
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.farhan.tanvir.androidcleanarchitecture.presentation.navigation.Screen
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.login.LoginItem
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppContentColor
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppThemeColor
import com.farhan.tanvir.domain.model.User


@Composable
fun LoginDetailsScreen(
    navController: NavHostController,
    onNavigateToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    // viewModel.getUserDetails(userID = userId.toInt())
    val userDetails by viewModel.selectedUser.collectAsState()

    val hasUser by viewModel.selectedToken.collectAsState()

    hasUser?.get("token").let{
        it?.let{
            if(it.asString.isNotEmpty()){
                //navController.navigate(route = Screen.Home.route)
                //Log.d("TIME123", "Has User Token.")
            }
        }
    }

    userDetails?.get("name").let{
        it?.let{
            if(it.asString.isNotEmpty()){
               // Log.d("TIME123", "Has User Details." + userDetails)
                //navController.navigate(route = Screen.Home.route)
                //onNavigateToHome()
            }
        }
    }

    fun navigateHome(){
        onNavigateToHome()
    }

    Scaffold(
        topBar={
            LoginDetailsTopBar(navController)
        },
        contentColor = MaterialTheme.colors.AppContentColor,
        backgroundColor = MaterialTheme.colors.AppThemeColor,
        content = {
           // userDetails?.let { LoginDetailsContent(navController = navController) }
            LoginItem(navController = navController, onNavigateToHome)
        })
}

