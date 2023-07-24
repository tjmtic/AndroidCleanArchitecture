package com.tiphubapps.ax.rain.presentation.helper

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun DismissibleNotificationBox(
    iconResId: Int,
    message: String,
    onDismiss: () -> Unit
) {
    var isVisible by remember { mutableStateOf(true) }
    val transition = rememberInfiniteTransition()
    val scale by transition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 300, easing = FastOutSlowInEasing)
        )
    )

    val heightAnimation = remember {
        Animatable(0f)
    }

    if (isVisible) {
        Box(
            modifier = Modifier
                .animateContentSize()
                .height(heightAnimation.value.dp)
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { onDismiss() }
                //.graphicsLayer(scaleX = scale, scaleY = scale)
                .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = iconResId),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = message,
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            heightAnimation.animateTo(60f, tween(durationMillis = 300, easing = FastOutLinearInEasing))
        } else {
            heightAnimation.animateTo(0f, tween(durationMillis = 500, easing = LinearOutSlowInEasing))
        }
    }
}