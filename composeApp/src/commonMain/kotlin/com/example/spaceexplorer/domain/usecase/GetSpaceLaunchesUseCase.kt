package com.example.spaceexplorer.domain.usecase

import com.example.spaceexplorer.domain.model.ResponseState
import com.example.spaceexplorer.domain.model.SpaceLaunchesViewEntity
import com.example.spaceexplorer.domain.repository.SpaceLaunchesRepository
import kotlinx.coroutines.flow.Flow

class GetSpaceLaunchesUseCase(private val spaceLaunchesRepository: SpaceLaunchesRepository) {

    operator fun invoke(): Flow<ResponseState<List<SpaceLaunchesViewEntity>>> {
        return spaceLaunchesRepository.getSpaceLaunches()
    }

}