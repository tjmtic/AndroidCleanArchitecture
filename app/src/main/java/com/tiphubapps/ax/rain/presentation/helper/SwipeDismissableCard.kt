package com.tiphubapps.ax.rain.presentation.helper

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tiphubapps.ax.rain.presentation.screen.details.LoginViewModel
import com.tiphubapps.ax.rain.presentation.screen.login.GPTLogin
import kotlinx.coroutines.launch

@Composable
fun SwipeDismissableCard(message: String, onDismiss: () -> Unit) {
    var offsetX by remember { mutableStateOf(0f) }
    val dismissOffset = 1000f
    val animatableOffset = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        Modifier
            .fillMaxWidth()
            .graphicsLayer(translationX = animatableOffset.value)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    offsetX += dragAmount
                    coroutineScope.launch {
                        animatableOffset.snapTo(offsetX)
                    }
                    Log.d("TIME123","SWIPPIUNG")
                    if (offsetX >= dismissOffset) {
                        onDismiss()
                    }
                }
            }
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = 4.dp,
            backgroundColor = Color.White,
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(message)
            }
        }
    }

    LaunchedEffect(offsetX) {
        if (offsetX >= dismissOffset) {
            animatableOffset.animateTo(targetValue = -3000f, animationSpec = tween(300))
        } else {
            animatableOffset.animateTo(targetValue = 0f, animationSpec = tween(300))
        }
    }
}

@Preview
@Composable
fun PreviewSwipeDismissableCard() {
    SwipeDismissableCard(
        message = "Swipe to Dismiss",
        {})
}
