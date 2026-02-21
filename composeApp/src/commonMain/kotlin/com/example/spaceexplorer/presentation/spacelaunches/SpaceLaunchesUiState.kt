package com.example.spaceexplorer.presentation.spacelaunches

import com.example.spaceexplorer.domain.model.SpaceLaunchViewEntity

data class SpaceLaunchesUiState(
    var isLoading: Boolean = false,
    var isRefreshing: Boolean = false,
    var spaceLaunches: List<SpaceLaunchViewEntity> = mutableListOf(),
    var errorMessage: String? = null
)
