package com.ssafy.data.repository

import com.ssafy.data.model.MappingDto
import com.ssafy.domain.model.ApiError
import com.ssafy.domain.model.ApiResult
import com.ssafy.util.NetworkException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

fun <T> buildFlow(
    result: Result<T>,
    substitute: Any,
    checkingCode: List<String> = listOf()
): Flow<ApiResult<Any>> = flow {
    val data = result.getOrNull()
    if (data != null) {
        emit(
            ApiResult.Success(
                if (data is MappingDto<*>) data.toDomain()
                else data
            )
        )
    } else {
        val exception = result.exceptionOrNull()
        if(exception is NetworkException){
            if (exception.code in checkingCode) {
                emit(ApiResult.Success(substitute))
            } else {
                emit(ApiResult.Failure(ApiError(exception.code, exception.message)))
            }
            emit(ApiResult.Failure(ApiError(exception.code, exception.message)))
        }
    }
}.onStart { emit(ApiResult.Loading()) }