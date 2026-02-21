package com.example.spaceexplorer.presentation.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKWebView

@Composable
actual fun PlatformWebView(
    url: String,
    modifier: Modifier
) {
    UIKitView(
        modifier = modifier.fillMaxSize(),
        factory = {
            val webView = WKWebView()
            NSURL.URLWithString(url)?.let { nsUrl ->
                val request = NSURLRequest.requestWithURL(nsUrl)
                webView.loadRequest(request)
            }
            webView
        }
    )
}