package com.tiphubapps.ax.rain.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tiphubapps.ax.rain.presentation.screen.login.LoginDetailsScreen
import com.tiphubapps.ax.rain.presentation.screen.details.UserDetailsScreen
import com.tiphubapps.ax.rain.presentation.screen.home.HomeScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {
            LoginDetailsScreen(/*navController = navController,*/
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