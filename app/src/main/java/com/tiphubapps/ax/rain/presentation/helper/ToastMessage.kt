package com.tiphubapps.ax.rain.presentation.helper

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tiphubapps.ax.rain.presentation.screen.details.LoginViewModel


@Composable
fun ToastMessage(message: String, duration: Int = Toast.LENGTH_SHORT,
                 onEvent: () -> Unit ) {
    val context = LocalContext.current
    var showToast by remember { mutableStateOf(true) }

    val interactionSource = remember { MutableInteractionSource() }
    var buttonScale by remember { mutableStateOf(1f) }

    LaunchedEffect(showToast) {
        if (showToast) {
            Toast.makeText(context, message, duration).show()
            showToast = false
        }
    }

    // Empty container composable to trigger the effect when showToast is updated
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x55000000))
    ) {Text(
        text = message,
        style = MaterialTheme.typography.h4,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 24.dp))}


                Button(
                onClick = { onEvent() ; Log.d("TIME123", "onevent button CLICK@$##@%@#%$#@$@")},
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
            text = "Close",
            style = MaterialTheme.typography.button,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp),
            color = Color.White,
        )
    }

}
