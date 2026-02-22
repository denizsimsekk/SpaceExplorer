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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.spaceexplorer.presentation.common.AppText
import com.example.spaceexplorer.presentation.base.BaseSpaceScreen
import spaceexplorer.composeapp.generated.resources.Res
import spaceexplorer.composeapp.generated.resources.bg_space_failed
import spaceexplorer.composeapp.generated.resources.bg_space_success

@Composable
fun SpaceLaunchDetailScreen(
    spaceLaunchId: String,
    onBackClick: () -> Unit,
    onUrlClicked: (String) -> Unit,
    viewModel: SpaceLaunchDetailsViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getSpaceLaunchDetail(id = spaceLaunchId)
    }

    BaseSpaceScreen(
        backgroundDrawable = if (uiState.spaceLaunchViewEntity?.success == true) Res.drawable.bg_space_success else Res.drawable.bg_space_failed,
        showTopBar = true,
        topBarTitle = uiState.spaceLaunchViewEntity?.name,
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

            if (uiState.spaceLaunchViewEntity != null) {
                Spacer(modifier = Modifier.weight(1f))

                AsyncImage(
                    model = uiState.spaceLaunchViewEntity?.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.size(200.dp),
                    contentScale = ContentScale.Fit,
                )

                AppText(
                    text = uiState.spaceLaunchViewEntity?.dateTime.orEmpty(),
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.padding(top = 8.dp)
                )
                AppText(
                    text = "Rocket: ${uiState.spaceLaunchViewEntity?.rocketDetails?.name}",
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.padding(top = 8.dp)
                )
                AppText(
                    text = "${uiState.spaceLaunchViewEntity?.rocketDetails?.description}",
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.padding(top = 2.dp),
                    textAlign = TextAlign.Center
                )
                AppText(
                    text = if (uiState.spaceLaunchViewEntity?.success == true) "Success" else "Failed",
                    color = if (uiState.spaceLaunchViewEntity?.success == true) Color(0xFF4CAF50) else Color(
                        0xFFE53935
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                uiState.spaceLaunchViewEntity?.articleUrl?.let {
                    AppText(
                        text = "Read more about this launch.",
                        underline = true,
                        modifier = Modifier.padding(bottom = 8.dp).clickable {
                            onUrlClicked.invoke(it)
                        }
                    )
                }
            } else {
                AppText(
                    text = "No records found.",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 16
                )
            }

        }

    }
}