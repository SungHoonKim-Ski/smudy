package com.ssafy.backend_dummy.user_service.util

class SingleResult<T>(
        var data: T,
        code: Int,
        message: String
) : CommonResult(code, message)
