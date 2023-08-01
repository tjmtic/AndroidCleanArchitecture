package com.tiphubapps.ax.rain.presentation.helper

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun ExternalBrowserLink(clickableText: String, url: String) {
    val context = LocalContext.current
    val openBrowser = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { it -> /* Handle result if needed */
        Log.d("TIME123", "Logging Browser Result Start:")
        Log.d("TIME123", it.data.toString())
        Log.d("TIME123", "Logging Browser Result End.")
    }

    Text(
        text = clickableText,
        style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.primary),
        modifier = Modifier.clickable {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            openBrowser.launch(intent)
        }
    )
}