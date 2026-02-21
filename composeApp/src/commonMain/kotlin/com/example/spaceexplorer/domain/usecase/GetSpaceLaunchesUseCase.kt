package com.example.spaceexplorer.domain.usecase

import com.example.spaceexplorer.domain.model.ResponseState
import com.example.spaceexplorer.domain.model.RocketViewEntity
import com.example.spaceexplorer.domain.model.SpaceLaunchesViewEntity
import com.example.spaceexplorer.domain.repository.RocketRepository
import com.example.spaceexplorer.domain.repository.SpaceLaunchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSpaceLaunchesUseCase(
    private val spaceLaunchesRepository: SpaceLaunchesRepository,
    private val rocketRepository: RocketRepository
) {

    operator fun invoke(): Flow<ResponseState<List<SpaceLaunchesViewEntity>>> = flow {
        spaceLaunchesRepository.getSpaceLaunches().collect { state ->
            when (state) {
                is ResponseState.Success -> {
                    val launches = state.data
                    val rocketsMap = mutableMapOf<String, RocketViewEntity>()
                    launches.map { it.rocket }.distinct().forEach { rocketId ->
                        when (val result = rocketRepository.getRocketDetails(rocketId)) {
                            is ResponseState.Success -> result.data?.let {
                                rocketsMap[rocketId] = it
                            }

                            is ResponseState.Error -> emit(ResponseState.Error(result.errorMessage))
                            is ResponseState.Loading -> {}
                        }
                    }
                    val launchesWithRockets = launches.map { launch ->
                        launch.copy(rocketDetails = rocketsMap[launch.rocket])
                    }
                    emit(ResponseState.Success(launchesWithRockets))
                }

                else -> emit(state)
            }
        }
    }
}