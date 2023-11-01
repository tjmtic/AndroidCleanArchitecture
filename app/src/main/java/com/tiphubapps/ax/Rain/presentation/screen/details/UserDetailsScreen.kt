package com.tiphubapps.ax.Rain.presentation.screen.details

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tiphubapps.ax.Rain.ui.theme.AppContentColor
import com.tiphubapps.ax.Rain.ui.theme.AppThemeColor


@Composable
fun UserDetailsScreen(
    navController: NavHostController,
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: UserDetailsViewModel = hiltViewModel(),
    parameters: String
) {
    //viewModel.getUserDetails(userID = userId.toInt())
    //val userDetails by viewModel.selectedUser.collectAsState()
    val uiState = viewModel.uiState.collectAsState()

    val token = viewModel.token.collectAsState()


    val currentUser = viewModel.selectedUser.collectAsState()
    Scaffold(
        topBar={
               UserDetailsTopBar(navController, {onNavigateToHome()})
        },
        contentColor = MaterialTheme.colors.AppContentColor,
        backgroundColor = MaterialTheme.colors.AppThemeColor,
        content = {
            //userDetails?.let { UserDetailsContent(it) }
            when(uiState.value) {
                is UserDetailsViewModel.LoginUiState.Invalid -> {
                    LaunchedEffect(uiState) {
                        println("invalid Login State, loggin out")
                        viewModel.logout()
                        onNavigateToLogin()
                    }
                }

                else -> {}
            }
            UserItem(
                user = currentUser.value,
                extra = token.value
            )


           /* Text("Parameter from deep link: $parameters")

            //SimpleWebView(title = "Privacy Policy", url = "http://www.tiphubapps.com/privacy/" )
            ExternalBrowserLink(clickableText = "Privacy Policy", url = "https://tiphubapps.com/privacy/" )

*/
        })
}

