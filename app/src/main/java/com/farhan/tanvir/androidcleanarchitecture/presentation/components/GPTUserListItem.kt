package com.farhan.tanvir.androidcleanarchitecture.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.farhan.tanvir.domain.model.User
import com.google.gson.JsonObject

@Composable
fun UserItem(user: JsonObject) {
        Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
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
                    painter = rememberImagePainter(user.get("coverImage")),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colors.primary)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

           /* Text(
                text = user.get("name"),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.weight(1f)
            )*/
        }
}

@Composable
@Preview
fun UserItemPreview(){
    var user = JsonObject().also{
        it.addProperty("name", "name test")
    }

    UserItem(user)
}
