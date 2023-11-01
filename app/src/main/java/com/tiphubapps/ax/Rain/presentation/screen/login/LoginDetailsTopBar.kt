package com.tiphubapps.ax.Rain.presentation.screen.details

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tiphubapps.ax.Rain.R
import com.tiphubapps.ax.Rain.ui.theme.AppContentColor
import com.tiphubapps.ax.Rain.ui.theme.AppThemeColor

@Composable
fun LoginDetailsTopBar(
    navController: NavHostController,
    //viewModel: LoginViewModel = hiltViewModel(),
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.AppThemeColor,
        navigationIcon = {
            IconButton(onClick = { Log.d("TIME123", "Console log on CLICK!");
                //viewModel.postLogin("jay@0260tech.com","Admin123!");
                //navController.navigate(route = Screen.Home.route)

            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Icon",
                    tint = MaterialTheme.colors.AppContentColor
                )
            }
        },
        title = {
            Text(
                text = stringResource(R.string.details),
                color = MaterialTheme.colors.AppContentColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h6
            )
        },
        elevation = 0.dp,
    )
}