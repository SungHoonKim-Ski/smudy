package com.ssafy.backend_dummy.search_service.util

class SingleResult<T>(
        var data: T,
        code: Int,
        message: String
) : CommonResult(code, message)
