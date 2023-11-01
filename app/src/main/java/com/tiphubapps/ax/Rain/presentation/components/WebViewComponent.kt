package com.tiphubapps.ax.Rain.presentation.components

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewComponent(url: String) {

    var backEnabled = remember { mutableStateOf(false) }
    var webView: WebView? = null
    AndroidView(
    //modifier = modifier,
    factory = { context ->
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = object : WebViewClient() {
                /*override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                    backEnabled = view.canGoBack()
                }*/
            }
            settings.javaScriptEnabled = true

            loadUrl(url)
            webView = this
        }
    }, update = {
        webView = it
    })

    /*BackHandler(enabled = backEnabled) {
        webView?.goBack()
    }*/
}