package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.confirm

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppContentColor
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppThemeColor


@Composable
fun ConfirmScreen(
    onBackPressed: () -> Unit,
) {
    Scaffold(
        topBar={
            ConfirmTopBar({onBackPressed()})
        },
        contentColor = MaterialTheme.colors.AppContentColor,
        backgroundColor = MaterialTheme.colors.AppThemeColor,
        content = {

        })
}

