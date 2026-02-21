package com.example.spaceexplorer.presentation.spacelaunches

import com.example.spaceexplorer.domain.usecase.GetSpaceLaunchesUseCase
import com.example.spaceexplorer.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SpaceLaunchesViewModel(private val getSpaceLaunchesUseCase: GetSpaceLaunchesUseCase) :
    BaseViewModel() {

    private val _uiState = MutableStateFlow(SpaceLaunchesUiState())
    val uiState = _uiState.asStateFlow()

    fun getSpaceLaunches(onRefresh: Boolean = false) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null, isRefreshing = onRefresh) }
        getSpaceLaunchesUseCase.invoke(onRefresh = onRefresh).collectData(
            onSuccess = { response ->
                _uiState.update {
                    it.copy(
                        spaceLaunches = response,
                        isLoading = false,
                        isRefreshing = false
                    )
                }
            },
            onError = { message ->
                _uiState.update {
                    it.copy(
                        errorMessage = message,
                        isLoading = false,
                        isRefreshing = false
                    )
                }
            }
        )
    }


}