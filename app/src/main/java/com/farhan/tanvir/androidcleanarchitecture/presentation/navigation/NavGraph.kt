package com.farhan.tanvir.androidcleanarchitecture.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.confirm.ConfirmScreen
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.confirm.ConflictScreen
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home.HomeScreen

@Composable
fun NavGraph(navController: NavHostController) {

    //NavGraph as the navigation source of truth
    fun navigateToConfirm(){ navController.navigate(Screen.Confirm.route) }
    fun navigateToConflict(){ navController.navigate(Screen.Conflict.route) }
    fun navigateBack(){ navController.popBackStack() }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen( onConfirm = {navigateToConfirm()},
                        onConflict = {navigateToConflict()}
            )
        }
        composable(route = Screen.Confirm.route) {
            ConfirmScreen( onBackPressed = {navigateBack()})
        }
        composable(route = Screen.Conflict.route) {
            ConflictScreen( onBackPressed = {navigateBack()})
        }
    }
}