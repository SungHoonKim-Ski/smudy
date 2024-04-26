package com.ssafy.backend_dummy.user_service.util

class ListResult<T>(
        var data: List<T>,
        code: Int,
        message: String
) : CommonResult(code, message)
