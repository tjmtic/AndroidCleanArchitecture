package com.tiphubapps.ax.rain.presentation.components

import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.tiphubapps.ax.rain.R
import com.google.gson.JsonObject
import com.tiphubapps.ax.domain.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.Timer
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GPTProfileScreen2(user: User?, url: String, onClick: () -> Unit, onTip: () -> Unit) {
    Log.d("TIME123", "PRIFLE SCREEN CHECK: " + url)
    var startX by remember { mutableStateOf(0f) }
    val threshold = 80.dp // Adjust the threshold as needed

    val interactionSource = remember { MutableInteractionSource() }
    var buttonScale by remember { mutableStateOf(1f) }


    var showTip by remember { mutableStateOf(false)}

    fun showTip(){
        showTip = !showTip;

        /*runBlocking {
            delay(2000)
            showTip = false;
        }*/
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .defaultMinSize(400.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    // Log.d("TIME123", "drag:start"+change.position)
                    // Log.d("TIME123", "drag:start"+change.pressed)
                    Log.d("TIME123", "drag:" + dragAmount.x + ":" + dragAmount.y)

                    if(dragAmount.y <= -30){
                        onTip()
                        showTip()
                    }
                    if (change.pressed) {
                        startX = dragAmount.x
                        //Log.d("TIME123", "init:"+change.pressed+":"+change.position)
                        //Log.d("TIME123", "previouis:"+change.previousPressed+":"+change.previousPosition)
                        //Log.d("TIME123", "drag:"+startX + ":" + dragAmount.y)

                    } else if (!change.pressed) {
                        Log.d("TIME123", "init2:" + change.pressed + ":" + change.position)
                        Log.d(
                            "TIME123",
                            "previouis2:" + change.previousPressed + ":" + change.previousPosition
                        )

                        val deltaX = dragAmount.x - startX
                        if (deltaX > threshold.toPx() || deltaX < -threshold.toPx()) {
                            //onSwipe()
                            onTip()
                        }
                        Log.d("TIME123", "drag:" + dragAmount.x + ":" + startX + ":" + deltaX)

                    }
                }
            }
    ) {
        // Background image
        /*user?.get("images")?.asJsonArray.let {
            it?.let {
                for (image in it) {
                    if (image.asJsonObject.get("type").asString.equals("cover")) {
                        Image(
                            painter = rememberImagePainter(
                                data = image.asJsonObject.get("url").asString,
                                builder = {
                                    crossfade(true)
                                    scale(Scale.FIT)
                                }),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds,
                        )
                    }
                }
            }
        }*/



        /*Image(
            painter = rememberImagePainter(
                data = url,
                builder = {
                    crossfade(true)
                    scale(Scale.FIT)
                }
            ),
            contentDescription = "title default",
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )*/


        if(showTip) {
            GifImageComponent()
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier
                    .size(154.dp)
                    .padding(5.dp),
                shape = CircleShape,
                border = BorderStroke(0.5.dp, Color.LightGray),
                elevation = 4.dp,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = url,
                        builder = {
                            crossfade(true)
                            scale(Scale.FIT)
                            placeholder(R.drawable.line_rain2a)
                        }
                    ),
                    contentDescription = "some",
                    modifier = Modifier.size(400.dp),
                    contentScale = ContentScale.FillBounds,
                )
            }

            // Profile icon with two lines of text
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    user?.name?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.h5.merge(
                                TextStyle(color = Color.White)
                            ),
                            textAlign = TextAlign.Start
                        )
                    }

                }
            }

            // Paragraph of text
            Text(
                text = "Tip Total:",
                style = MaterialTheme.typography.body1.merge(
                    TextStyle(color = Color.White)
                ),
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
//            style = TextStyle(color = Color.White)
            )
            user?.payerBalance?.let {
                Text(
                    text = " ${it}",
                    style = MaterialTheme.typography.body1.merge(
                        TextStyle(color = Color.White)
                    ),
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
//            style = TextStyle(color = Color.White)
                )
            }

            //GifImageComponent()

            /*Button(
                onClick = { onTip(); showTip() },
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(.95f)
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "SEND TIP",
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = Color.White
                )
            }*/



            Button(
                onClick = { onClick() },
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
                    text = "Disconnect",
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
fun PreviewProfileScreen2() {
    GPTProfileScreen2(null, "", {}, {})
}