package com.abyxcz.disneycodechallenge.disneycodechallenge_timmcardle.presentation.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abyxcz.disneycodechallenge.disneycodechallenge_timmcardle.presentation.components.CheckboxComponent
import com.abyxcz.disneycodechallenge.disneycodechallenge_timmcardle.ui.theme.ItemBackgroundColor
import com.abyxcz.disneycodechallenge.domain.model.User


@Composable
fun UserListItem(user: User,
                 onCheckboxSelected: (Boolean) -> Unit) {

    Card(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth(),
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.ItemBackgroundColor
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth()
                .clickable {
                           onCheckboxSelected(!user.selected);
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            CheckboxComponent(text = user.name, user.selected, onCheckboxSelected)
        }
    }
}