package com.ssafy.authservice.common.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
        val status: HttpStatus,
        val code: String,
        val message: String
) {
    // USER
    JWT_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "U001", "Jwt Expired")
    , UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "U004", "TOKEN UNAUTHORIZED")
    , JWT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S003", "JWT ERROR")
}
