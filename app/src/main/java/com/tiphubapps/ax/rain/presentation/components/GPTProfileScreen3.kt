package com.tiphubapps.ax.rain.presentation.components

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tiphubapps.ax.rain.R
import com.tiphubapps.ax.domain.model.User
import coil.compose.rememberImagePainter
import coil.size.Scale


@Composable
fun GPTProfileScreen3(user: User, qrImage: Bitmap, onClick : () -> Unit) {

   // var cover by rememberSaveable( mutableStateOf(""))
    val interactionSource = remember { MutableInteractionSource() }
    var buttonScale by remember { mutableStateOf(1f) }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color(0xFF000000))
    ) {
        // Background image
        /*Image(
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
        )*/

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            //item {
            // Profile icon with two lines of text
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier,
                    //.size(154.dp)
                    // .padding(5.dp),
                    shape = CircleShape,
                    border = BorderStroke(0.5.dp, Color.LightGray),
                    elevation = 4.dp,
                    //  color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = user.profileImage,
                            builder = {
                                crossfade(true)
                                scale(Scale.FIT)
                            }
                        ),
                        contentDescription = "title default",
                        modifier = Modifier.size(64.dp),
                        contentScale = ContentScale.Crop
                    )

                }
                Column(
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    user.name?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.h5.merge(TextStyle(color = Color.White)),
                            textAlign = TextAlign.Start
                        )
                    }
                    user.receiverBalance?.let {
                        Text(
                            text = "Total Earned:" + user.receiverBalance.toString(),
                            style = MaterialTheme.typography.body1.merge(TextStyle(color = Color.White)),
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
            // }

            //  item {
            // Paragraph of text
            Text(
                text = "Display your barcode for users to scan and tip you!",
                style = TextStyle(color = Color.White),
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )


            /*Image(
                painter = rememberImagePainter(
                    data = user.coverImage),
                contentDescription = "title default",
                modifier = Modifier.size(256.dp),
            )*/
            Surface(
                modifier = Modifier,
                //.size(154.dp)
                // .padding(5.dp),
                //shape = CircleShape,
                border = BorderStroke(2.5.dp, Color.Black),
                elevation = 14.dp,
                //  color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
            ){
            Image(
                painter = rememberImagePainter(
                    data = qrImage,
                    builder = {
                        crossfade(true)
                        scale(Scale.FIT)
                    }),
                contentDescription = null,
                modifier = Modifier
                    .width(375.dp)
                    .height(375.dp),
                contentScale = ContentScale.FillWidth
            )
        }
          //  }

          //  item {
                // Call-to-action button
                /*Button(
                    onClick = { /* Handle button click */ },
                    modifier = Modifier.padding(16.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    Text(
                        text = "Following",
                        style = MaterialTheme.typography.button,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }*/

                //ButtonComponent("Contributors", {onClick()}, true)
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
                    text = "Contributors",
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = Color.White
                )
            }
         //   }
        }
    }
}
/*
@Preview
@Composable
fun PreviewProfileScreen3() {
    GPTProfileScreen3(User(
        0,
        0,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        "https://tip-hub.s3.amazonaws.com/users/img/5e22b8a4bf397f08932de490-profile.png",
        "https://tip-hub.s3.amazonaws.com/users/img/5e22b8a4bf397f08932de490-profile.png"),
        "https://tip-hub.s3.amazonaws.com/users/img/5e22b8a4bf397f08932de490-profile.png",
         {}, )
}*/
