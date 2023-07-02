package com.tiphubapps.ax.rain.presentation.screen.home

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tiphubapps.ax.rain.R
import com.tiphubapps.ax.rain.ui.theme.AppContentColor
import com.tiphubapps.ax.rain.ui.theme.AppThemeColor

@Composable
fun HomeTopBar(onNavigateToProfile: () -> Unit,
               onShowSend: () -> Unit,
               onShowReceive: () -> Unit,
               onShowCamera: () -> Unit,
) {
    val context = LocalContext.current

    fun navigateToProfile(){
        onNavigateToProfile()
    }
    fun showMessage(context: Context) {
        /*val browserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://github.com/Farhandroid/AndroidCleanArchitecture")
        )
        ContextCompat.startActivity(context, browserIntent, null)*/

        navigateToProfile()

    }
    
    fun showSend(){
        onShowSend()
    }

    fun showReceive(){
        onShowReceive()
    }

    fun showCamera(){
        onShowCamera()
    }

    TopAppBar(
        backgroundColor = MaterialTheme.colors.AppThemeColor,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colors.AppContentColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h5
            )
        },
        elevation = 0.dp,
        actions = {

           /* IconButton(onClick = { showCamera() }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Favourite Icon",
                    tint = Color.Red
                )
            }*/
            IconButton(onClick = { showMessage(context = context) }) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Favourite Icon",
                    tint = Color.Red
                )
            }

        },
    )
}

