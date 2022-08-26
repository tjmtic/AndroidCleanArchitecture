package com.abyxcz.disneycodechallenge.disneycodechallenge_timmcardle.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val BlueGrey900 = Color(0xFF263238)
val Grey100 = Color(0xFFF5F5F5)

val BlueCustom = Color(0xFF0079BC)
val BlueCustomDark = Color(0xFF00233C)
val BlueCustomLight = Color(0xFFB7E4F7)

val Colors.AppThemeColor
    get() = if (isLight) White else Black

val Colors.AppContentColor
    get() = if (isLight) Black else White

val Colors.TitleColor
    get() = if (isLight) Black else White

val Colors.ItemBackgroundColor get() = if (isLight) White else BlueGrey900
