package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.login

import androidx.hilt.navigation.compose.hiltViewModel
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.details.LoginViewModel

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.RatingComponent
import com.farhan.tanvir.androidcleanarchitecture.presentation.navigation.Screen
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.ItemBackgroundColor
import com.google.gson.JsonObject


@Composable
fun LoginItem(navController: NavHostController, onNavigateToHome: () -> Unit, viewModel: LoginViewModel = hiltViewModel()) {
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
                    Log.d("TIME123", "Console Log Clicked on USER CARD!");
                    //viewModel.postLogin("", "")
                    viewModel.postLogin("jay@0260tech.com","Admin123!");
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .height(IntrinsicSize.Max)
                    .padding(
                        end = 2.dp,
                    )) {
                Text(text = "Test Text1", style = MaterialTheme.typography.body1)

                Spacer(modifier = Modifier.height(4.dp))

                Text(text = "Test Text2", style = MaterialTheme.typography.body1)

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Test Text3", style = MaterialTheme.typography.body1)
            }
        }
    }
}