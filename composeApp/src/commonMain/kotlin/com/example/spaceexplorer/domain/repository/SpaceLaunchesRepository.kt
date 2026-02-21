package com.example.spaceexplorer.domain.repository

import com.example.spaceexplorer.domain.model.ResponseState
import com.example.spaceexplorer.domain.model.SpaceLaunchViewEntity
import kotlinx.coroutines.flow.Flow

interface SpaceLaunchesRepository {

    fun getSpaceLaunches(): Flow<ResponseState<List<SpaceLaunchViewEntity>>>

    suspend fun getSpaceLaunchDetails(id: String): SpaceLaunchViewEntity?

    suspend fun saveLaunchesToLocal(launches: List<SpaceLaunchViewEntity>)

    suspend fun getLaunchesFromLocal(): List<SpaceLaunchViewEntity>?
}