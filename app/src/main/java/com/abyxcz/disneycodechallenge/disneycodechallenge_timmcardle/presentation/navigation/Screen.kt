package com.abyxcz.disneycodechallenge.disneycodechallenge_timmcardle.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object Confirm : Screen("confirm_screen")
    object Conflict : Screen("conflict_screen")
}
