package com.example.spaceexplorer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.spaceexplorer.presentation.spacelaunches.SpaceLaunchesScreen
import com.example.spaceexplorer.presentation.spacelaunches.SpaceLaunchesViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

import spaceexplorer.composeapp.generated.resources.Res
import spaceexplorer.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {
        val viewModel = koinInject<SpaceLaunchesViewModel>()

        SpaceLaunchesScreen(viewModel = viewModel)
    }
}