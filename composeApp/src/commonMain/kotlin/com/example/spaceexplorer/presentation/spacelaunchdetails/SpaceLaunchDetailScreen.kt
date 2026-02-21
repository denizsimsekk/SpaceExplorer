package com.example.spaceexplorer.presentation.spacelaunchdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
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
import org.jetbrains.compose.resources.painterResource
import spaceexplorer.composeapp.generated.resources.Res
import spaceexplorer.composeapp.generated.resources.bg_space_failed
import spaceexplorer.composeapp.generated.resources.bg_space_success

@Composable
fun SpaceLaunchDetailScreen(
    spaceLaunchViewEntity: SpaceLaunchViewEntity,
    onUrlClicked: (String) -> Unit
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(if (spaceLaunchViewEntity?.success == true) Res.drawable.bg_space_success else Res.drawable.bg_space_failed),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize().padding(12.dp)
                .padding(12.dp)
                .safeDrawingPadding()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black.copy(alpha = 0.5f)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            AsyncImage(
                model = spaceLaunchViewEntity.links.patch?.small,
                contentDescription = null,
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Fit,
            )
            HorizontalDivider(modifier = Modifier.size(16.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AppText(
                    text = spaceLaunchViewEntity.name,
                    modifier = Modifier.padding(top = 4.dp),
                    fontSize = 22
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
                if (spaceLaunchViewEntity.articleUrl.isNullOrEmpty().not()) {
                    AppText(
                        text = "See article for this launch",
                        underline = true,
                        modifier = Modifier.clickable {
                            onUrlClicked.invoke(spaceLaunchViewEntity.articleUrl)

                        })
                }
            }


        }
    }
}