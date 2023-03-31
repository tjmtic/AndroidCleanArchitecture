package com.farhan.tanvir.androidcleanarchitecture.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object Forgot : Screen("forgot_screen")
    object Signup : Screen("signup_screen")
    object Home : Screen("home_screen")
    object UserDetails : Screen("user_details_screen/{userId}") {
        fun passUserId(userId: String) = "user_details_screen/$userId"
    }
}
