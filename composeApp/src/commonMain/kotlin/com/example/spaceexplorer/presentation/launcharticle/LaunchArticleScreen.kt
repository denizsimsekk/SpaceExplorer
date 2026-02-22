package com.example.spaceexplorer.presentation.launcharticle

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.spaceexplorer.presentation.base.BaseSpaceScreen
import com.example.spaceexplorer.presentation.common.PlatformWebView

@Composable
fun LaunchArticleScreen(
    url: String,
    onBackClick: () -> Unit
) {

    BaseSpaceScreen(
        showTopBar = true,
        onBackClick = onBackClick
    ) {
        PlatformWebView(url = url,
            modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp)
                .clip(RoundedCornerShape(12.dp))
        )
    }
}