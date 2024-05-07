package com.ssafy.authservice.common.exception

import org.springframework.http.HttpStatus

data class ErrorResponse(
        var status: HttpStatus,
        var message: String,
        var code: String
) {
    // 보조 생성자 1
    constructor(errorCode: ErrorCode) : this(
            status = errorCode.status,
            message = errorCode.message,
            code = errorCode.code
    )

    // 보조 생성자 2
    constructor(errorCode: ErrorCode, errorMessage: String) : this(
            status = errorCode.status,
            message = errorMessage,
            code = errorCode.code
    )
}