package com.example.spaceexplorer.data.repository

import com.example.spaceexplorer.domain.model.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.io.IOException

abstract class BaseRepository {

    protected fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Flow<ResponseState<T>> = flow {
        emit(ResponseState.Loading)
        val result = apiCall()
        emit(ResponseState.Success(result))
    }.catch { error ->
        emit(ResponseState.Error(error.message.orEmpty()))
    }.flowOn(Dispatchers.IO)

    protected suspend fun <T> safeApiCallResult(
        apiCall: suspend () -> T,
    ): ResponseState<T> {
        return try {
            ResponseState.Success(apiCall())
        } catch (e: Exception) {
            ResponseState.Error(e.message.orEmpty())
        }
    }

}