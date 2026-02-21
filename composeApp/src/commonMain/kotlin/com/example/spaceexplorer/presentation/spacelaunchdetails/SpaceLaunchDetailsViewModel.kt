package com.example.spaceexplorer.presentation.spacelaunchdetails

import androidx.lifecycle.viewModelScope
import com.example.spaceexplorer.domain.usecase.GetSpaceLaunchDetailUseCase
import com.example.spaceexplorer.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SpaceLaunchDetailsViewModel(private val getSpaceLaunchDetailUseCase: GetSpaceLaunchDetailUseCase) :
    BaseViewModel() {

    private val _uiState = MutableStateFlow(SpaceLaunchDetailsUiState())
    val uiState = _uiState.asStateFlow()

    fun getSpaceLaunchDetail(id: String) {
        viewModelScope.launch {
            val spaceLaunchDetail = getSpaceLaunchDetailUseCase.invoke(id = id)
            _uiState.update { it.copy(spaceLaunchViewEntity = spaceLaunchDetail) }
        }
    }


}