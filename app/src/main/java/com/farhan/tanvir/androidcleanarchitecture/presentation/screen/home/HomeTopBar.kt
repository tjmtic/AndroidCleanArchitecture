package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.farhan.tanvir.androidcleanarchitecture.R
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppContentColor
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppThemeColor

@Composable
fun HomeTopBar(
) {
    val context = LocalContext.current
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

            IconButton(onClick = {  }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favourite Icon",
                    tint = Color.Red
                )
            }
        }
    )
}
