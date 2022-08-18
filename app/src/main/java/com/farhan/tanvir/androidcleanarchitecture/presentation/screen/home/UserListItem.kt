package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.CheckboxComponent
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.ItemBackgroundColor
import com.farhan.tanvir.domain.model.User


@Composable
fun UserListItem(user: User,
                 onCheckboxSelected: (Boolean) -> Unit) {

    Card(
        modifier = Modifier
            .padding(top = 8.dp)
            .height(180.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
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

            Column(
                Modifier
                .height(IntrinsicSize.Max)
                .padding(
                    end = 2.dp,
                )) {

                CheckboxComponent(text = user.name, user.selected, onCheckboxSelected)
            }
        }
    }
}