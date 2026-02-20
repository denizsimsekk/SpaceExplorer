package com.example.spaceexplorer.data.remote

import com.example.spaceexplorer.data.model.SpaceLaunchResponseItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiService(private val client: HttpClient) {

    suspend fun getSpaceLaunches(): List<SpaceLaunchResponseItem> =
        client.get("launches").body<List<SpaceLaunchResponseItem>>()

}