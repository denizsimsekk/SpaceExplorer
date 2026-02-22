package com.example.spaceexplorer.data.remote

import com.example.spaceexplorer.data.model.RocketResponseDto
import com.example.spaceexplorer.data.model.SpaceLaunchResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiService(private val client: HttpClient) : SpaceExplorerApi {

    override suspend fun getSpaceLaunches(): List<SpaceLaunchResponseDto> =
        client.get(urlString = "launches").body<List<SpaceLaunchResponseDto>>()

    override suspend fun getRocketDetails(id: String): RocketResponseDto =
        client.get(urlString = "rockets/$id").body<RocketResponseDto>()

}