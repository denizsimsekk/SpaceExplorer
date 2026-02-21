package com.example.spaceexplorer.domain.usecase

import com.example.spaceexplorer.domain.model.ResponseState
import com.example.spaceexplorer.domain.model.SpaceLaunchViewEntity
import com.example.spaceexplorer.domain.repository.SpaceLaunchesRepository

class GetSpaceLaunchDetailUseCase(private val spaceLaunchesRepository: SpaceLaunchesRepository) {

    operator suspend fun invoke(id: String): SpaceLaunchViewEntity? {
        return spaceLaunchesRepository.getSpaceLaunchDetails(id = id)
    }

}