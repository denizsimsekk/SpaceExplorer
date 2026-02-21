package com.example.spaceexplorer.data.repository

import com.example.spaceexplorer.data.remote.ApiService
import com.example.spaceexplorer.database.SpaceExplorerDatabase
import com.example.spaceexplorer.domain.model.ResponseState
import com.example.spaceexplorer.domain.model.SpaceLaunchViewEntity
import com.example.spaceexplorer.domain.repository.SpaceLaunchesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SpaceLaunchesRepositoryImpl(
    private val apiService: ApiService,
    private val database: SpaceExplorerDatabase
) : SpaceLaunchesRepository, BaseRepository() {

    private val cacheKey = "launches"

    override fun getSpaceLaunches(): Flow<ResponseState<List<SpaceLaunchViewEntity>>> {
        return safeApiCall { apiService.getSpaceLaunches().map { it.toViewEntity() } }
    }

    override suspend fun getSpaceLaunchDetails(id: String): SpaceLaunchViewEntity? {
        return getLaunchesFromLocal()?.find { it.id == id }
    }

    override suspend fun saveLaunchesToLocal(launches: List<SpaceLaunchViewEntity>) {
        withContext(Dispatchers.IO) {
            val json = Json.encodeToString(launches)
            database.spaceLaunchesCacheQueries.insertOrReplace(cacheKey, json)
        }
    }

    override suspend fun getLaunchesFromLocal(): List<SpaceLaunchViewEntity>? {
        return withContext(Dispatchers.IO) {
            val json = database.spaceLaunchesCacheQueries.getCache(cacheKey).executeAsOneOrNull()
            json?.let { Json.decodeFromString<List<SpaceLaunchViewEntity>>(it) }
        }
    }
}