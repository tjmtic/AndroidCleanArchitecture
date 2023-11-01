package com.tiphubapps.ax.Rain.presentation.screen.login

import androidx.hilt.navigation.compose.hiltViewModel
import com.tiphubapps.ax.Rain.presentation.screen.details.LoginViewModel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.tiphubapps.ax.Rain.BuildConfig
import com.tiphubapps.ax.Rain.R
import com.tiphubapps.ax.Rain.presentation.components.ButtonComponent
import com.tiphubapps.ax.Rain.presentation.components.LoginHeaderComponent
import com.tiphubapps.ax.Rain.presentation.components.TextButtonComponent


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