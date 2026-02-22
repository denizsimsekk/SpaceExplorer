package com.example.spaceexplorer.data.repository

import com.example.spaceexplorer.data.remote.SpaceExplorerApi
import com.example.spaceexplorer.domain.model.ResponseState
import com.example.spaceexplorer.domain.model.RocketViewEntity
import com.example.spaceexplorer.domain.repository.RocketRepository

class RocketRepositoryImpl(
    private val api: SpaceExplorerApi,
) : RocketRepository,
    BaseRepository() {

    override suspend fun getRocketDetails(id: String): ResponseState<RocketViewEntity?> {
        return safeApiCallResult {
            api.getRocketDetails(id).toViewEntity()
        }
    }

}




