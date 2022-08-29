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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.farhan.tanvir.androidcleanarchitecture.BuildConfig
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.*
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
        backgroundColor = Color.Blue
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            /*Image(
                painter = rememberImagePainter(
                    data = BuildConfig.POSTER_URL, builder = {
                        crossfade(true)
                        scale(Scale.FIT)
                    }),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.FillWidth
            )

            Text(
                text = "RAIN",
                style = MaterialTheme.typography.h2
            )*/
            LoginHeaderComponent("RAIN", BuildConfig.POSTER_URL)
            //input
            //input
            //button - login -
                        // viewModel.postLogin(input1, input2)


            LoginItem( onLoginClick, enabled)

            Column(
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TextButtonComponent(text = "Don't have an account? Sign up here", { showSignupClick() }, enabled)
            }

            Column(
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .fillMaxWidth(),
                //verticalAlignment = Alignment.CenterVertically,
                //verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TextButtonComponent(text = "Forgot Password", { showForgotClick() }, enabled)
            }
        }
    }
}