package com.example.spaceexplorer.data.repository

import com.example.spaceexplorer.data.remote.ApiService
import com.example.spaceexplorer.database.RocketCache
import com.example.spaceexplorer.database.SpaceExplorerDatabase
import com.example.spaceexplorer.domain.model.ResponseState
import com.example.spaceexplorer.domain.model.RocketViewEntity
import com.example.spaceexplorer.domain.repository.RocketRepository

class RocketRepositoryImpl(
    private val apiService: ApiService,
) : RocketRepository,
    BaseRepository() {

    override suspend fun getRocketDetails(id: String): ResponseState<RocketViewEntity?> {
        return safeApiCallResult {
            apiService.getRocketDetails(id).toViewEntity()
        }
    }


}




