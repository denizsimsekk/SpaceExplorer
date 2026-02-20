package com.example.spaceexplorer.presentation.spacelaunches

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SpaceLaunchesScreen(viewModel: SpaceLaunchesViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getSpaceLaunches()
    }

}