package com.abyxcz.disneycodechallenge.disneycodechallenge_timmcardle.presentation.screen.confirm

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.abyxcz.disneycodechallenge.disneycodechallenge_timmcardle.ui.theme.AppContentColor
import com.abyxcz.disneycodechallenge.disneycodechallenge_timmcardle.ui.theme.AppThemeColor


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

