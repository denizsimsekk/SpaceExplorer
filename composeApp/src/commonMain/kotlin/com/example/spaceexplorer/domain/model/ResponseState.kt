package com.example.spaceexplorer.domain.model

sealed class ResponseState<out T> {
    object Loading : ResponseState<Nothing>()
    data class Success<T>(val data: T) : ResponseState<T>()
    data class Error(val errorMessage: String) : ResponseState<Nothing>()
}
