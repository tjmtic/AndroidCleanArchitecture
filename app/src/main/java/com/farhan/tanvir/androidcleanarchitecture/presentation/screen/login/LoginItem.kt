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
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
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
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.RatingComponent
import com.farhan.tanvir.androidcleanarchitecture.presentation.navigation.Screen
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.ItemBackgroundColor
import com.google.gson.JsonObject


@Composable
fun LoginItem(onLoginClick: () -> Unit,
              enabled: Boolean,
                viewModel: LoginViewModel = hiltViewModel()) {

    var username by remember { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Card(
        modifier = Modifier
            .padding(top = 8.dp)
            .height(IntrinsicSize.Max)
            .fillMaxWidth(),
        elevation = 0.dp,
        backgroundColor = Color.Blue
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .height(IntrinsicSize.Max).fillMaxWidth()
                    .padding(
                        end = 2.dp,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it ; viewModel.updateUsername(username)},
                    label = { Text("Username", style = TextStyle(color = White)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = White,
                        unfocusedBorderColor = White,
                        cursorColor = White,
                        focusedLabelColor = White
                    )
                )


                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                        value = password,
                        onValueChange = { password = it ; viewModel.updatePassword(password) },
                    label = { Text("Enter Password", style = TextStyle(color = White)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = White,
                        unfocusedBorderColor = White,
                        cursorColor = White,
                        focusedLabelColor = White
                    ),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )



                Spacer(modifier = Modifier.height(50.dp))

                ButtonComponent(text = "Log In", {onLoginClick()}, enabled)
            }
        }
    }
}