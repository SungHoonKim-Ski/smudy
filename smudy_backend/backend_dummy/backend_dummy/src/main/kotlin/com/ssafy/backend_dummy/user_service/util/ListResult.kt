package com.ssafy.backend_dummy.user_service.util

class ListResult<T>(
        code: Int,
        message: String,
        data: List<T>
) : CommonResult(code, message)
