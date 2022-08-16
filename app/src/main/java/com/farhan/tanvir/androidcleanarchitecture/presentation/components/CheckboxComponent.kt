package com.farhan.tanvir.androidcleanarchitecture.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.farhan.tanvir.domain.model.User

@Composable
fun CheckboxComponent(text: String, value: Boolean, onValueChanged: (Boolean) -> Unit) {
    Row(modifier = Modifier.padding(8.dp)) {
        //val isChecked = rememberSaveable { mutableStateOf(value) }

        Checkbox(
            checked = value,//isChecked.value,
            onCheckedChange = onValueChanged,
            enabled = true,
            colors = CheckboxDefaults.colors(Color.Green)
        )
        Text(text = text)
    }
}