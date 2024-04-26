package com.ssafy.backend_dummy.auth_service.util

data class ListResult<T> (
    private val data: List<T>
) : CommonResult()
