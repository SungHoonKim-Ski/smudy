package com.ssafy.authservice.util


class ListResult<T>(
        code: Int,
        message: String,
        data: List<T>
) : CommonResult(code, message)
