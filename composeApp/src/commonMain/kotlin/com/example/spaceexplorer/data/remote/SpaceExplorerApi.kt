package com.example.spaceexplorer.data.remote

import com.example.spaceexplorer.data.model.RocketResponseDto
import com.example.spaceexplorer.data.model.SpaceLaunchResponseDto

interface SpaceExplorerApi {
    suspend fun getSpaceLaunches(): List<SpaceLaunchResponseDto>
    suspend fun getRocketDetails(id: String): RocketResponseDto
}
