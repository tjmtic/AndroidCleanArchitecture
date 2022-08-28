package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.farhan.tanvir.androidcleanarchitecture.R
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppContentColor
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppThemeColor
import com.google.gson.JsonObject

@Composable
fun HomeBottomBar(onShowSend: () -> Unit,
               onShowReceive: () -> Unit,
                  onShowCamera: () -> Unit,
) {
    val context = LocalContext.current
    
    fun showSend(){
        onShowSend()
    }

    fun showReceive(){
        onShowReceive()
    }

    fun showCamera(){
        onShowCamera()
    }


    BottomAppBar(
        backgroundColor = MaterialTheme.colors.AppThemeColor,

        elevation = 0.dp,
        content = {


            IconButton(onClick = { showSend() }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favourite Icon",
                    tint = Color.Red
                )
            }

            IconButton(onClick = { showCamera() }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Favourite Icon",
                    tint = Color.Red
                )
            }

            IconButton(onClick = { showReceive() }) {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = "Favourite Icon",
                    tint = Color.Red
                )
            }

        }
    )
}

