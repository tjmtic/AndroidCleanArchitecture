package com.tiphubapps.ax.Rain.presentation.helper

import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.viewinterop.AndroidView
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable

@Composable
fun SimpleWebView(title: String, url: String) {
    val context = LocalContext.current
    var showWebView by remember { mutableStateOf(false) }

    if (showWebView) {
        AndroidView(
            factory = { webViewContext ->
                WebView(webViewContext).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    settings.javaScriptEnabled = true
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    webChromeClient = WebChromeClient()
                    webViewClient = WebViewClient()
                    loadUrl(url)
                }
            },
            modifier = Modifier.fillMaxSize()
        ) {
            // Handle back navigation within the WebView
            it.setOnKeyListener { _, keyCode, event ->
                if (keyCode == android.view.KeyEvent.KEYCODE_BACK && it.canGoBack()) {
                    it.goBack()
                    return@setOnKeyListener true
                }
                else if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
                    showWebView = false
                    return@setOnKeyListener true
                }

                false
            }
        }
    } else {
        Text(
            text = title,
            style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.secondary),
            modifier = Modifier.clickable { showWebView = true }
        )
    }
}
