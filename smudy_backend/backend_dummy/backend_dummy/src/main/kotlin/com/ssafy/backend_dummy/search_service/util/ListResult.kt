package com.ssafy.backend_dummy.search_service.util

class ListResult<T>(
        var data: List<T>,
        code: Int,
        message: String
) : CommonResult(code, message)
