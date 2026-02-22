package com.example.spaceexplorer.presentation.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.spaceexplorer.presentation.common.AppText
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import spaceexplorer.composeapp.generated.resources.Res
import spaceexplorer.composeapp.generated.resources.bg_space

@Composable
fun BaseSpaceScreen(
    backgroundDrawable: DrawableResource = Res.drawable.bg_space,
    modifier: Modifier = Modifier,
    showTopBar: Boolean = false,
    topBarTitle: String? = null,
    onBackClick: () -> Unit = {},
    content: @Composable () -> Unit
) {

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(backgroundDrawable),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
        ) {
            if (showTopBar) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable(onClick = onBackClick)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    topBarTitle?.let { title ->
                        AppText(
                            text = title,
                            modifier = Modifier.padding(start = 8.dp),
                            fontSize = 20
                        )
                    }
                }
            }
            content()
        }
    }
}
