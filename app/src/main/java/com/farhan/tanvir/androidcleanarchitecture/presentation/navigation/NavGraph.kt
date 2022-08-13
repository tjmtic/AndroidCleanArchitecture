package com.farhan.tanvir.androidcleanarchitecture.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.details.LoginDetailsScreen
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.details.UserDetailsScreen
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home.HomeScreen
import com.farhan.tanvir.androidcleanarchitecture.util.Constant

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Login.route) {
            LoginDetailsScreen(navController = navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.UserDetails.route,
            arguments = listOf(navArgument(Constant.USER_DETAILS_ARGUMENT_KEY) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString(Constant.USER_DETAILS_ARGUMENT_KEY)
                ?.let { UserDetailsScreen(it,navController) }
        }
    }
}