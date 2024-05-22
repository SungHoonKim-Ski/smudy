package com.ssafy.authservice.common.exception

import org.springframework.http.HttpStatus

class CustomException(
    private val exceptionType: ExceptionType
) : RuntimeException() {

    override val message: String
        get() = exceptionType.message

    val code: Int
        get() = exceptionType.code

    val status: HttpStatus
        get() = HttpStatus.valueOf(exceptionType.code)
}
