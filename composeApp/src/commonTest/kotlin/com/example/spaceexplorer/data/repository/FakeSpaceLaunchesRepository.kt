package com.example.spaceexplorer.data.repository

import com.example.spaceexplorer.domain.model.ResponseState
import com.example.spaceexplorer.domain.model.SpaceLaunchViewEntity
import com.example.spaceexplorer.domain.repository.SpaceLaunchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeSpaceLaunchesRepository : SpaceLaunchesRepository {

    var launches: List<SpaceLaunchViewEntity>? = null
    var spaceLaunchesFlow: Flow<ResponseState<List<SpaceLaunchViewEntity>>> =
        flowOf(ResponseState.Success(emptyList()))
    var saveLaunchesCalledWith: List<SpaceLaunchViewEntity>? = null

    override fun getSpaceLaunches(): Flow<ResponseState<List<SpaceLaunchViewEntity>>> = spaceLaunchesFlow

    override suspend fun getSpaceLaunchDetails(id: String): SpaceLaunchViewEntity? {
        return launches?.find { it.id == id }
    }

    override suspend fun saveLaunchesToLocal(launches: List<SpaceLaunchViewEntity>) {
        saveLaunchesCalledWith = launches
        this.launches = launches
    }

    override suspend fun getLaunchesFromLocal(): List<SpaceLaunchViewEntity>? = launches
}