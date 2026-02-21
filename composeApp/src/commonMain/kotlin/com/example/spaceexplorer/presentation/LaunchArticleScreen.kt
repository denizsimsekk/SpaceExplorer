package com.example.spaceexplorer.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.spaceexplorer.presentation.common.PlatformWebView

@Composable
fun LaunchArticleScreen(url: String) {
    PlatformWebView(url = url, modifier = Modifier.fillMaxSize())
}