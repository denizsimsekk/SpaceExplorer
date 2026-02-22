package com.example.spaceexplorer.data.repository

import com.example.spaceexplorer.domain.model.ResponseState
import com.example.spaceexplorer.domain.model.RocketViewEntity
import com.example.spaceexplorer.domain.repository.RocketRepository

class FakeRocketRepository : RocketRepository {

    private val rocketMap = mutableMapOf<String, RocketViewEntity>()
    var errorForId: String? = null

    var errorMessage: String = "Rocket error"

    fun setRocketError(id: String, message: String = "Rocket error") {
        errorForId = id
        errorMessage = message
    }


    fun setRocket(rocket: RocketViewEntity) {
        rocketMap[rocket.id] = rocket
    }

    override suspend fun getRocketDetails(id: String): ResponseState<RocketViewEntity?> {
        errorForId?.let { if (id == it) return ResponseState.Error(errorMessage) }
        return ResponseState.Success(rocketMap[id])
    }
}