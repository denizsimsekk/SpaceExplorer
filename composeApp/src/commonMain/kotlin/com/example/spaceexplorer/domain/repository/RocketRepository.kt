package com.example.spaceexplorer.domain.repository

import com.example.spaceexplorer.domain.model.ResponseState
import com.example.spaceexplorer.domain.model.RocketViewEntity
import kotlinx.coroutines.flow.Flow

interface RocketRepository {

    suspend fun getRocketDetails(id: String): ResponseState<RocketViewEntity?>

}
