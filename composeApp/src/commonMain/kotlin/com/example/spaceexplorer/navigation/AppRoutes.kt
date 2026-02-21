package com.example.spaceexplorer.navigation

import androidx.navigation3.runtime.NavKey
import com.example.spaceexplorer.domain.model.SpaceLaunchViewEntity
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute : NavKey {
    @Serializable
    data object SpaceLaunches : AppRoute

    @Serializable
    data class LaunchDetails(val spaceLaunchesViewEntity: SpaceLaunchViewEntity) : AppRoute

    @Serializable
    data class LaunchArticleScreen(val url: String) : AppRoute
}


