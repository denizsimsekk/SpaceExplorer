package com.example.spaceexplorer.presentation.spacelaunchdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.spaceexplorer.domain.model.SpaceLaunchViewEntity
import com.example.spaceexplorer.presentation.common.AppText
import com.example.spaceexplorer.presentation.base.BaseSpaceScreen
import spaceexplorer.composeapp.generated.resources.Res
import spaceexplorer.composeapp.generated.resources.bg_space_failed
import spaceexplorer.composeapp.generated.resources.bg_space_success

@Composable
fun SpaceLaunchDetailScreen(
    spaceLaunchViewEntity: SpaceLaunchViewEntity,
    onBackClick: () -> Unit,
    onUrlClicked: (String) -> Unit
) {

    BaseSpaceScreen(
        backgroundDrawable = if (spaceLaunchViewEntity.success) Res.drawable.bg_space_success else Res.drawable.bg_space_failed,
        showTopBar = true,
        topBarTitle = spaceLaunchViewEntity.name,
        onBackClick = onBackClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black.copy(alpha = 0.5f)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))

            AsyncImage(
                model = spaceLaunchViewEntity.imageUrl,
                contentDescription = null,
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Fit,
            )

            AppText(
                text = spaceLaunchViewEntity.dateTime,
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.padding(top = 8.dp)
            )
            AppText(
                text = "Rocket: ${spaceLaunchViewEntity.rocketDetails?.name}",
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.padding(top = 8.dp)
            )
            AppText(
                text = "${spaceLaunchViewEntity.rocketDetails?.description}",
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.padding(top = 2.dp),
                textAlign = TextAlign.Center
            )
            AppText(
                text = if (spaceLaunchViewEntity.success) "Success" else "Failed",
                color = if (spaceLaunchViewEntity.success) Color(0xFF4CAF50) else Color(
                    0xFFE53935
                ),
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            if (spaceLaunchViewEntity.articleUrl.isNullOrEmpty().not()) {
                AppText(
                    text = "See article for this launch",
                    underline = true,
                    modifier = Modifier.padding(bottom = 8.dp).clickable {
                        onUrlClicked.invoke(spaceLaunchViewEntity.articleUrl)
                    }
                )
            }
        }

    }
}