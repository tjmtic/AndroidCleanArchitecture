package com.tiphubapps.ax.rain.presentation.screen.login

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tiphubapps.ax.rain.R
import com.tiphubapps.ax.rain.presentation.helper.DismissibleNotificationBox
import com.tiphubapps.ax.rain.presentation.helper.ExternalBrowserLink
import com.tiphubapps.ax.rain.presentation.helper.LoadingOverlay
import com.tiphubapps.ax.rain.presentation.helper.SimpleWebView
import com.tiphubapps.ax.rain.presentation.helper.SwipeDismissableCard
import com.tiphubapps.ax.rain.presentation.helper.ToastMessage
import com.tiphubapps.ax.rain.presentation.screen.details.LoginViewModel

@Composable
fun GPTLogin(
    state: LoginViewModel.LoginViewState,
    onLoginClick: (String, String) -> Unit,
    onSignupClick: () -> Unit,
    onForgotClick: () -> Unit,
    onEvent: (LoginViewModel.LoginViewEvent) -> Unit ,
    onEventEmail: (String) -> Unit,
    onEventPassword: (String) -> Unit,
    onEventLogin: () -> Unit,
){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val interactionSource = remember { MutableInteractionSource() }
    var buttonScale by remember { mutableStateOf(1f) }

    var showToast = state.error


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
    ) {
        /*Image(
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
        )*/

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "RAIN",
                style = MaterialTheme.typography.h4,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Text(
                text = "Let's dive into your account",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(horizontal = 8.dp),
                color = Color.White
            )

            Text(
                text = "Email",
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.fillMaxWidth(),
                color = Color.White
            )
            TextField(
                value = state.email,
                onValueChange = { /*email = it ;*/  onEventEmail(it)},
                label = { Text(text = "Email", color = Color.White) },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .background(color = Color.DarkGray, RoundedCornerShape(15.dp))
                    .border(2.dp, Color.DarkGray, RoundedCornerShape(15.dp))
            )



            Text(
                text = "Password",
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.fillMaxWidth(),
                color = Color.White
            )

            TextField(
                value = password,
                onValueChange = { password = it ;/* onEvent(LoginViewModel.LoginViewEvent.PasswordChanged(it))*/ onEventPassword(it) },
                label = { Text(text = "Password", color = Color.White) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .background(color = Color.DarkGray, RoundedCornerShape(15.dp))
                    .border(2.dp, Color.DarkGray, RoundedCornerShape(15.dp))
            )

            Button(
                onClick = { /*onLoginClick(email, password)*//*onEvent(LoginViewModel.LoginViewEvent.LoginClicked)*/onEventLogin() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta),
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(buttonScale)
                    .pointerInput(interactionSource) {
                        detectTapGestures(
                            onPress = { buttonScale = 0.95f },
                            //l = { buttonScale = 1f }
                        )
                        //onPointerUp { buttonScale = 1f }
                        //onPointerCancel { buttonScale = 1f }
                    }
                    .padding(vertical = 16.dp)
                    .background(color = Color.Magenta, shape = RoundedCornerShape(25.dp))

            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp),
                    color = Color.White,
                )
            }

            Button(
                onClick = { onForgotClick() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(buttonScale)
                    .pointerInput(interactionSource) {
                        detectTapGestures(
                            onPress = { buttonScale = 0.95f },
                            //l = { buttonScale = 1f }
                        )
                        //onPointerUp { buttonScale = 1f }
                        //onPointerCancel { buttonScale = 1f }
                    }
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "Forgot Password?",
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = Color.Magenta
                )
            }

            Button(
                onClick = { onSignupClick() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(buttonScale)
                    .pointerInput(interactionSource) {
                        detectTapGestures(
                            onPress = { buttonScale = 0.95f },
                            //l = { buttonScale = 1f }
                        )
                        //onPointerUp { buttonScale = 1f }
                        //onPointerCancel { buttonScale = 1f }
                    }
                    .padding(vertical = 16.dp)
                    .background(color = Color.Black, shape = RoundedCornerShape(25.dp))

            ) {
                Text(
                    text = "Don't Have an Account?",
                    //style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = Color.White,
                )
                Text(
                    text = "Click Here",
                    //style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = Color.Magenta,
                )
            }


            ExternalBrowserLink(clickableText = "Privacy Policy", url = "https://tiphubapps.com/privacy/" )
        }
    }

    //Loading Indicator Overlay
    when(state.isLoading){
        true -> {Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x55000000))
        )
            LoadingOverlay(isLoading = true)
        }

        else -> {}
    }

    //Custom Message Box
    if (showToast.length > 0) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DismissibleNotificationBox(
                iconResId = R.drawable.profile_icon,
                message = showToast,
                onDismiss = { onEvent(LoginViewModel.LoginViewEvent.ConsumeError) }
            )
            ToastMessage(
                message = showToast,
                onEvent = { onEvent(LoginViewModel.LoginViewEvent.ConsumeError) })

            SwipeDismissableCard(showToast) {

                {onEvent(LoginViewModel.LoginViewEvent.ConsumeError) }
            }
            Log.d("TIME123", "LOGGING TOAST:" + showToast)
        }
    }


}

@Preview
@Composable
fun PreviewLoginScreen() {
    GPTLogin(LoginViewModel.LoginViewState(
        name="",
        email = "ttest e",
        password = "",
        isLoading = false,
        error = "",
        errors = emptyList()),
        { _, _->},{},{},{},{},{},{})
}
