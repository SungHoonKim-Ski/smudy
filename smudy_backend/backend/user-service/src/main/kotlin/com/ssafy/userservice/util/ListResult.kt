package com.ssafy.userservice.util

class ListResult<T>(
        code: Int,
        message: String,
        data: List<T>
) : CommonResult(code, message)
