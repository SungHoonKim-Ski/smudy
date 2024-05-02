package com.ssafy.userservice.exception.error

import org.springframework.http.HttpStatus

data class ErrorResponse(
        var status: HttpStatus,
        var message: String,
        var code: String
) {
    // 보조 생성자 1
    constructor(errorCode: ErrorCode) : this(
            status = errorCode.getHttpStatus(),
            message = errorCode.getMessage(),
            code = errorCode.getCode()
    )

    // 보조 생성자 2
    constructor(errorCode: ErrorCode, errorMessage: String) : this(
            status = errorCode.getHttpStatus(),
            message = errorMessage,
            code = errorCode.getCode()
    )
}