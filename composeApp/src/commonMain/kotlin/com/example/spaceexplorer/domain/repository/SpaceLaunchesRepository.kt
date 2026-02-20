package com.example.spaceexplorer.domain.repository

import com.example.spaceexplorer.domain.model.ResponseState
import com.example.spaceexplorer.domain.model.SpaceLaunchesViewEntity
import kotlinx.coroutines.flow.Flow

interface SpaceLaunchesRepository {

    fun getSpaceLaunches(): Flow<ResponseState<List<SpaceLaunchesViewEntity>>>

}