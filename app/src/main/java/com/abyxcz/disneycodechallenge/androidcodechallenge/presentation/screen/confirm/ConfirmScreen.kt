package com.abyxcz.disneycodechallenge.androidcodechallenge.presentation.screen.confirm

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.abyxcz.disneycodechallenge.androidcodechallenge.ui.theme.AppContentColor
import com.abyxcz.disneycodechallenge.androidcodechallenge.ui.theme.AppThemeColor


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

