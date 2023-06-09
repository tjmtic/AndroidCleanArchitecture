import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.farhan.tanvir.androidcleanarchitecture.R
import kotlinx.coroutines.delay

@Composable
fun GPTSignUp(onSignupClick: (String, String) -> Unit,
              showLoginClick: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val interactionSource = remember { MutableInteractionSource() }
    var buttonScale by remember { mutableStateOf(1f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF36496D))
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.h4,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Name", color = Color.White) },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email", color = Color.White) },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password", color = Color.White) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text(text = "Confirm Password", color = Color.White) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Confirm Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            Button(
                onClick = { onSignupClick(email, password) },
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
                    text = "Sign Up",
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = Color.White
                )
            }

            Button(
                onClick = { showLoginClick() },
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
                    text = "Back to Log In",
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSignUpScreen() {
    GPTSignUp({_,_->},{})
}
