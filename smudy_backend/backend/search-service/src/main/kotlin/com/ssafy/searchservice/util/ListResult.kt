package com.ssafy.searchservice.util

class ListResult<T>(
        code: Int,
        message: String,
        data: List<T>
) : CommonResult(code, message)
