package com.tiphubapps.ax.rain.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavDeepLink
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tiphubapps.ax.rain.presentation.screen.login.LoginDetailsScreen
import com.tiphubapps.ax.rain.presentation.screen.details.UserDetailsScreen
import com.tiphubapps.ax.rain.presentation.screen.home.HomeScreen
import com.tiphubapps.ax.rain.util.Constant

@Composable
fun NavGraph(navController: NavHostController) {

    val deepLinks = listOf(
        NavDeepLink.Builder()
            .setUriPattern("rainapp://user_extras_screen/{parameters")
            .setAction("android.intent.action.VIEW")
            .build()
    )

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
        deepLinks = deepLinks
        ) {
            UserDetailsScreen(navController,
            onNavigateToHome = { navController.navigate(route = Screen.Home.route) },
                onNavigateToLogin = { navController.navigate(route = Screen.Login.route)},
            parameters = "")
        /*backStackEntry ->
            backStackEntry.arguments?.getString(Constant.USER_DETAILS_ARGUMENT_KEY)
                ?.let { UserDetailsScreen(it,navController) }*/
        }
        composable(
            route = Screen.UserExtras.route,
            /*arguments = listOf(navArgument(Constant.USER_DETAILS_ARGUMENT_KEY) {
                type = NavType.StringType
            })*/
            deepLinks = deepLinks
        ) {
            backStackEntry ->
                backStackEntry.arguments?.getString(Constant.USER_EXTRAS_ARGUMENT_KEY)
                    ?.let { UserDetailsScreen(navController,
                        onNavigateToHome = { navController.navigate(route = Screen.Home.route) },
                        onNavigateToLogin = { navController.navigate(route = Screen.Login.route) },
                        parameters = it
                    )}

        }
    }
}