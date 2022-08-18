package com.farhan.tanvir.androidcleanarchitecture.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.farhan.tanvir.androidcleanarchitecture.R
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppContentColor
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.BlueCustomDark

@Composable
fun PopupComponent(title: String, text: String, onIconClick: () -> Unit) {

    ConstraintLayout (modifier = Modifier.background(BlueCustomDark).padding(20.dp)){
        val (titleText, messageText, popupIcon) = createRefs()

        Row(
            modifier = Modifier
                .fillMaxWidth()

                .constrainAs(titleText) {
                    top.linkTo(parent.top)
                }
        ) {
            Text(text = title, style = MaterialTheme.typography.body1, color = Color.White)
        }
        Row(
            modifier = Modifier
                .width(250.dp)
                .constrainAs(messageText) {
                    top.linkTo(titleText.bottom)
                }
        ) {
            Text(text = text, style = MaterialTheme.typography.body2, color = Color.White)
        }
        Column(
            modifier = Modifier
                //.fillMaxWidth()
                .constrainAs(popupIcon) {
                    end.linkTo(parent.end, margin = 10.dp)
                    top.linkTo(parent.top, margin = 10.dp)
                }
        ) {
            IconButton(onClick = { onIconClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Icon",
                    tint = Color.White
                )
            }
        }
    }


}