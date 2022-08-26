package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.login

import androidx.hilt.navigation.compose.hiltViewModel
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.details.LoginViewModel

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.ButtonComponent
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.RatingComponent
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
            .height(IntrinsicSize.Max)
            .fillMaxWidth(),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.ItemBackgroundColor
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .height(IntrinsicSize.Max)
                    .padding(
                        end = 2.dp,
                    )) {

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it ; viewModel.updateUsername(username)},
                    label = { Text("Username") }
                )



                Spacer(modifier = Modifier.height(8.dp))

                ButtonComponent(text = "Forgot Button", {validateButtonClick()}, enabled)

                //Back to Login
                ButtonComponent(text = "User a Login", {showLoginClick()}, enabled)

                //Forgot
                ButtonComponent(text = "Let me Sign Up", {showSignupClick()}, enabled)
            }
        }
    }
}