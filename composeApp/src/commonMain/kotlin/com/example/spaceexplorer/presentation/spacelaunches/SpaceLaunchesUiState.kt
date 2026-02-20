package com.example.spaceexplorer.presentation.spacelaunches

import com.example.spaceexplorer.domain.model.SpaceLaunchesViewEntity

data class SpaceLaunchesUiState(
    var isLoading: Boolean = false,
    var spaceLaunches: List<SpaceLaunchesViewEntity> = mutableListOf(),
    var errorMessage: String? = null
)
