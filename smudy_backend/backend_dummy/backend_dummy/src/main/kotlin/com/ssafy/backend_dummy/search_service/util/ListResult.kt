package com.ssafy.backend_dummy.search_service.util

class ListResult<T>(
        code: Int,
        message: String,
        data: List<T>
) : CommonResult(code, message)
