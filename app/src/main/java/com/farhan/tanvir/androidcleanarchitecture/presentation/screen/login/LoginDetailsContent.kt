package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.farhan.tanvir.androidcleanarchitecture.BuildConfig
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.ButtonComponent
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.RatingComponent
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.ReleaseDateComponent
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.login.LoginItem
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppThemeColor
import com.farhan.tanvir.domain.model.User

@Composable
fun LoginDetailsContent(
    navController: NavHostController,
    onLoginClick: () -> Unit,
    showSignupClick: () -> Unit,
    showForgotClick: () -> Unit,
    enabled: Boolean,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    Card(
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.AppThemeColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {

            //input
            //input
            //button - login -
                        // viewModel.postLogin(input1, input2)
            LoginItem( onLoginClick, enabled)

            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                ButtonComponent(text = "Signup", { showSignupClick() }, enabled)
            }

            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                ButtonComponent(text = "Forgot", { showForgotClick() }, enabled)
            }
        }
    }
}