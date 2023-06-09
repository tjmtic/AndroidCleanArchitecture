package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.signup

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.farhan.tanvir.androidcleanarchitecture.BuildConfig
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.ButtonComponent
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.LoginHeaderComponent
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.RatingComponent
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.TextButtonComponent
import com.farhan.tanvir.androidcleanarchitecture.presentation.navigation.Screen
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.ItemBackgroundColor
import com.google.gson.JsonObject


@Composable
fun SignupItem(onSignupClick: (String, String) -> Unit,
               showLoginClick: () -> Unit,
               showForgotClick: () -> Unit,
              enabled: Boolean,
                viewModel: LoginViewModel = hiltViewModel()) {

    var username by remember { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var usernameConfirm by remember { mutableStateOf("") }
    var passwordConfirm by rememberSaveable { mutableStateOf("") }

    fun validateSignup(){

        if(username.equals(usernameConfirm) && password.equals(passwordConfirm)) {

            onSignupClick(username, password)
        }
        else{
            println("Invalid Login!!!!!!!!!!!!! Mismatched username or password.")
        }
    }

    Card(
        modifier = Modifier
            .padding(top = 8.dp)
            .height(IntrinsicSize.Max)
            .fillMaxWidth(),
        elevation = 0.dp,
        backgroundColor = Color.Blue,
    ) {
        Column(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
                LoginHeaderComponent("SIGNUP", BuildConfig.POSTER_URL)


                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it ; /*viewModel.updateUsername(username)*/},
                    label = { Text("Username", style = TextStyle(color = Color.White)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.White,
                        focusedLabelColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = usernameConfirm,
                    onValueChange = { usernameConfirm = it ;},
                    label = { Text("Confirm Username", style = TextStyle(color = Color.White)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.White,
                        focusedLabelColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))


                OutlinedTextField(
                        value = password,
                        onValueChange = { password = it ; /*viewModel.updatePassword(password) */},
                        label = { Text("Password", style = TextStyle(color = Color.White)) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.White,
                        focusedLabelColor = Color.White
                    )
                    )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = passwordConfirm,
                    onValueChange = { passwordConfirm = it ; },
                    label = { Text("Confirm password", style = TextStyle(color = Color.White)) },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.White,
                        focusedLabelColor = Color.White
                    )
                )


                Spacer(modifier = Modifier.height(50.dp))

                ButtonComponent(text = "Signup", {validateSignup()}, enabled)

                //Back to Login
                TextButtonComponent(text = "Already have an account? Log in here", {showLoginClick()}, enabled)

                //Forgot
               // TextButtonComponent(text = "Actually I Forgot", {showForgotClick()}, enabled)

        }
    }
}