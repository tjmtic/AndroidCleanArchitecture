package com.tiphubapps.ax.rain.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.google.gson.JsonArray
import com.google.gson.JsonObject

@Composable
fun FullSendItemComponent(user: JsonObject?) {


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
        Spacer(modifier = Modifier.height(8.dp))

        Column(
            Modifier
                .height(IntrinsicSize.Max)
                /*.padding(
                    end = 2.dp,
                )*/
        ) {
            user?.get("name")?.asString?.let {
                Text(
                    text = it,
//                    style = TextStyle(color = Color.White)
                )
            }
            //Text(text = "it", style = MaterialTheme.typography.body1)

        }
    }


    user?.get("payerBalance")?.asString?.let {
        Text(
            text = "Tip Total: ${it}",
//            style = TextStyle(color = Color.White)
        )
    }


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
fun test(){
    val user = JsonObject();
    val images = JsonArray();
    user.addProperty("name", "test");
    user.addProperty("payerBalance", 100);
    user.add("images", images);

    FullSendItemComponent(user)
}