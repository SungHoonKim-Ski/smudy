package com.ssafy.searchservice.util

data class SingleResult<T>(
        override var code: Int,
        override var message: String,
        var data: T
) : CommonResult(code, message)
