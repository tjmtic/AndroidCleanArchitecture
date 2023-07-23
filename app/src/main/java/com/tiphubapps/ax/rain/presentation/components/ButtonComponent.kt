package com.tiphubapps.ax.rain.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.runtime.getValue

enum class ButtonState {
    IDLE, PRESSED
}
@Composable
fun ButtonComponent(text: String, onClick: () -> Unit, enabled: Boolean) {
    val buttonState = remember { mutableStateOf(ButtonState.IDLE) } //3

    val animWidth by animateDpAsState(targetValue = if(buttonState.value == ButtonState.IDLE) 200.dp else 50.dp)

    Button(
        enabled = enabled,
        onClick = { onClick(); buttonState.value =  if(buttonState.value == ButtonState.IDLE) ButtonState.PRESSED else ButtonState.IDLE },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta),
        contentPadding = PaddingValues(
            start = 30.dp,
            top = 12.dp,
            end = 30.dp,
            bottom = 12.dp
        ),
        modifier = Modifier
            .width(animWidth)
            .padding(16.dp, 0.dp, 16.dp, 34.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        shape = RoundedCornerShape(25.dp)
    ) {
        Text(text, style = TextStyle(color = Color.White))
    }
}