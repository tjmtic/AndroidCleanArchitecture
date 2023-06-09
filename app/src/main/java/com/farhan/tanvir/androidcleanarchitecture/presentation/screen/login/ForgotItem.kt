package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.login

import androidx.hilt.navigation.compose.hiltViewModel
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.details.LoginViewModel

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.farhan.tanvir.androidcleanarchitecture.BuildConfig
import com.farhan.tanvir.androidcleanarchitecture.R
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.ButtonComponent
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.LoginHeaderComponent
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.RatingComponent
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.TextButtonComponent
import com.farhan.tanvir.androidcleanarchitecture.presentation.navigation.Screen
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.ItemBackgroundColor
import com.google.gson.JsonObject


@Composable
fun ForgotItem(onButtonClick: (String) -> Unit,
               showLoginClick: () -> Unit,
               showSignupClick: () -> Unit,
              enabled: Boolean,
                viewModel: LoginViewModel = hiltViewModel()) {

    var username by remember { mutableStateOf("") }

    fun validateButtonClick(){

            onButtonClick(username)

    }

    Card(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
        elevation = 0.dp,
    ) {
        Image(painter = painterResource(id = R.mipmap.rain_raindrop_barcode),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxHeight()
            )
            Column(
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                LoginHeaderComponent("FORGOT PASSWORD", BuildConfig.POSTER_URL)
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it; /*viewModel.updateUsername(username) */},
                    label = { Text("Account Email", style = TextStyle(color = Color.White)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.White,
                        focusedLabelColor = Color.White
                    )
                )



                Spacer(modifier = Modifier.height(50.dp))

                ButtonComponent(text = "Reset Password", { validateButtonClick() }, enabled)

                //Back to Login
                TextButtonComponent(text = "Cancel", { showLoginClick() }, enabled)



        }
    }
}