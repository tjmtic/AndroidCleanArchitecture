package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.farhan.tanvir.androidcleanarchitecture.R
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.ButtonComponent
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.PopupComponent
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppContentColor
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppThemeColor
import com.farhan.tanvir.domain.model.User
import com.google.gson.JsonObject

@Composable
fun HomeBottomBar( text: String,
                   onClickButton: () -> Unit = { },
                   enabled: Boolean = false,
                   onClickPopup: () -> Unit = { },
                   displayPopup: Boolean = false
) {
    BottomAppBar(
        backgroundColor = MaterialTheme.colors.AppThemeColor,
        elevation = 0.dp,
        content = {
            //May display action button or popup dialog
            if (!displayPopup)
            ButtonComponent(text, onClickButton, enabled)
            else
            PopupComponent(stringResource(id = R.string.info), { onClickPopup() })
        }
    )
}

