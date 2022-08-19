package com.abyxcz.disneycodechallenge.androidcodechallenge.presentation.screen.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abyxcz.disneycodechallenge.androidcodechallenge.ui.theme.AppContentColor
import com.abyxcz.disneycodechallenge.androidcodechallenge.ui.theme.AppThemeColor
import com.abyxcz.disneycodechallenge.androidcodechallenge.R

@Composable
fun HomeTopBar(
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.AppThemeColor,
        navigationIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Icon",
                    tint = MaterialTheme.colors.AppContentColor
                )
        },
        title = {
            Text(
                text = stringResource(R.string.select_guests),
                color = MaterialTheme.colors.AppContentColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h6
            )
        },
        elevation = 0.dp,
    )
}
