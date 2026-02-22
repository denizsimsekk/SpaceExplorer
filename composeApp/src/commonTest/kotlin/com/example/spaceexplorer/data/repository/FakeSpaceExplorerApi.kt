package com.example.spaceexplorer.data.repository

import com.example.spaceexplorer.data.model.RocketResponseDto
import com.example.spaceexplorer.data.model.SpaceLaunchResponseDto
import com.example.spaceexplorer.data.remote.SpaceExplorerApi

class FakeSpaceExplorerApi(
    private val launches: List<SpaceLaunchResponseDto> = emptyList(),
    private val launchesError: Throwable? = null,
    private val rockets: Map<String, RocketResponseDto> = emptyMap(),
    private val rocketError: Throwable? = null
) : SpaceExplorerApi {

    override suspend fun getSpaceLaunches(): List<SpaceLaunchResponseDto> {
        launchesError?.let { throw it }
        return launches
    }

    override suspend fun getRocketDetails(id: String): RocketResponseDto {
        rocketError?.let { throw it }
        return rockets[id] ?: createDefaultRocket(id)
    }

    private fun createDefaultRocket(id: String) = RocketResponseDto(
        active = true,
        boosters = 0,
        company = "SpaceX",
        cost_per_launch = 50000000,
        country = "US",
        description = "Rocket description",
        first_flight = "2020-01-01",
        id = id,
        name = "Falcon 9",
        stages = 2,
        success_rate_pct = 98,
        type = "rocket",
        wikipedia = "https://example.com"
    )
}
