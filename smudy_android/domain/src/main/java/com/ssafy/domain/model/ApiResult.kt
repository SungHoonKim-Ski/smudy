package com.ssafy.domain.model

sealed class ApiResult<T> {
    class Success<T>(val data: T) : ApiResult<T>()
    class Loading<T>():ApiResult<T>()
    class Failure<T>(val apiError: ApiError) : ApiResult<T>()
}