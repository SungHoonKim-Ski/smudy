package com.ssafy.backend_dummy.study_service.util

class ListResult<T>(
        code: Int,
        message: String,
        data: List<T>
) : CommonResult(code, message)