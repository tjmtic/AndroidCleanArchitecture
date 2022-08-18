package com.farhan.tanvir.androidcleanarchitecture.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.details.UserDetailsScreen
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home.HomeScreen

@Composable
fun NavGraph(navController: NavHostController) {

    fun navigateToConfirm(){ println("Navigate Confirm / Success") }//navController.navigate("confirm") }

    fun navigateToConflict(){ println("Navigate Conflict / Error") }//navController.navigate("conflict") }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen( onConfirm = {navigateToConfirm()},
                        onConflict = {navigateToConflict()}
            )
        }
        composable(
            route = Screen.UserDetails.route,
            arguments = listOf(navArgument("CONSTANT_navarg") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("CONSTANT_navarg")
                ?.let { UserDetailsScreen(it,navController) }
        }
    }
}