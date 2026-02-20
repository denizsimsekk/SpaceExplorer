package com.example.spaceexplorer.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceexplorer.domain.model.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    fun <T> Flow<ResponseState<T>>.collectData(
        onSuccess: (T) -> Unit,
        onError: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            collect { result ->
                when (result) {
                    is ResponseState.Success -> onSuccess(result.data)
                    is ResponseState.Error -> onError(result.errorMessage)
                    is ResponseState.Loading -> {}
                }
            }
        }
    }

}