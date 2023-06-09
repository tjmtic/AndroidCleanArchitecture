package com.farhan.tanvir.androidcleanarchitecture.presentation.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.google.gson.JsonArray
import com.google.gson.JsonObject

@Composable
fun EmptySendItemComponent(user: JsonObject?) {

    Spacer(modifier = Modifier.height(4.dp))

    Row(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        user?.get("images")?.asJsonArray.let {
            it?.let {
                for (image in it) {
                    if (image.asJsonObject.get("type").asString.equals("profile")) {
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
                                    data = image.asJsonObject.get("url").asString,
                                    builder = {
                                        crossfade(true)
                                        scale(Scale.FIT)
                                    }),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(50.dp),
                                contentScale = ContentScale.FillWidth
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Column(
            Modifier
                .height(IntrinsicSize.Max)
                .padding(
                    end = 2.dp,
                ),
        ) {
            user?.get("name")?.asString?.let {
                Text(
                    text = it,
                    style = TextStyle(color = Color.White)
                )
            }
            //Text(text = "it", style = MaterialTheme.typography.body1)

            user?.get("payerBalance")?.asString?.let {
                Text(
                    text = "Remaining Tokens: ${it}",
                    style = TextStyle(color = Color.White)
                )
            }
        }
    }
    Text(style = TextStyle(color = Color.White),text = "Select a user to tip by scanning a barcode or picking from your recent list")


    Text(style = TextStyle(color = Color.White),text = "The image below is your cover image. Contributors will see this photo while tipping you. Tap the profile button to change this photo in the profile menu.")

    user?.get("images")?.asJsonArray.let {
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
                        modifier = Modifier
                            .width(375.dp)
                            .height(375.dp),
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun testEmpty(){
    val user = JsonObject();
    val images = JsonArray();
    //{"_id":"62737248654982002a02d2aa","url":"https://tip-hub.s3.amazonaws.com/users/img/5e22b8a4bf397f08932de490-profile.png","name":"5e22b8a4bf397f08932de490-profile.png","type":"profile","user":"5e22b8a4bf397f08932de490","createdAt":"2022-05-05T06:44:24.462Z","updatedAt":"2022-05-18T02:43:05.433Z","__v":0},{"_id":"62737321654982002a02d2f2","url":"https://tip-hub.s3.amazonaws.com/users/img/5e22b8a4bf397f08932de490-cover.png","name":"5e22b8a4bf397f08932de490-cover.png","type":"cover","user":"5e22b8a4bf397f08932de490","createdAt":"2022-05-05T06:48:01.740Z","updatedAt":"2023-03-22T22:00:38.952Z","__v":0}

    //"url":"https://tip-hub.s3.amazonaws.com/users/img/5e22b8a4bf397f08932de490-profile.png","name":"5e22b8a4bf397f08932de490-profile.png","type":"profile",
    //"url":"https://tip-hub.s3.amazonaws.com/users/img/5e22b8a4bf397f08932de490-cover.png","name":"5e22b8a4bf397f08932de490-cover.png","type":"cover",
    val image1 = JsonObject();
    image1.addProperty("url", "https://tip-hub.s3.amazonaws.com/users/img/5e22b8a4bf397f08932de490-profile.png");
    image1.addProperty("type", "profile");

    val image2 = JsonObject();
    image2.addProperty("url", "https://tip-hub.s3.amazonaws.com/users/img/5e22b8a4bf397f08932de490-cover.png");
    image2.addProperty("type", "cover");

    images.add(image1)
    images.add(image2)

    user.addProperty("name", "test");
    user.addProperty("payerBalance", 100);
    user.add("images", images);

    EmptySendItemComponent(user)
}