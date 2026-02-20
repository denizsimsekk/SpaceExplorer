package com.example.spaceexplorer.data.repository

import com.example.spaceexplorer.data.remote.ApiService
import com.example.spaceexplorer.domain.model.ResponseState
import com.example.spaceexplorer.domain.model.SpaceLaunchesViewEntity
import com.example.spaceexplorer.domain.repository.SpaceLaunchesRepository
import kotlinx.coroutines.flow.Flow

class SpaceLaunchesRepositoryImpl(private val apiService: ApiService) : SpaceLaunchesRepository,
    BaseRepository() {
    override fun getSpaceLaunches(): Flow<ResponseState<List<SpaceLaunchesViewEntity>>> {
        return safeApiCall { apiService.getSpaceLaunches().map { it.toViewEntity() } }
    }
}