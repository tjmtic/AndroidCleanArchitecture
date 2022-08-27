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
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {
            LoginDetailsScreen(navController = navController,
                                onNavigateToHome = { navController.navigate(route = Screen.Home.route) })
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController,
                        onNavigateToProfile = { navController.navigate(route = Screen.UserDetails.route) })
        }
        composable(
            route = Screen.UserDetails.route,
            /*arguments = listOf(navArgument(Constant.USER_DETAILS_ARGUMENT_KEY) {
                type = NavType.StringType
            })*/
        ) {
            UserDetailsScreen(navController,
            onNavigateToHome = { navController.navigate(route = Screen.Home.route) },
                onNavigateToLogin = { navController.navigate(route = Screen.Login.route) })
        /*backStackEntry ->
            backStackEntry.arguments?.getString(Constant.USER_DETAILS_ARGUMENT_KEY)
                ?.let { UserDetailsScreen(it,navController) }*/
        }
    }
}