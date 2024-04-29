package com.ssafy.data.model

data class DefaultResponse<T>(
    val code: Int,
    val message: String,
    val data: T
)
