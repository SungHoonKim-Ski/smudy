package com.ssafy.userservice.exception.error

import org.springframework.http.HttpStatus

enum class CommonErrorCode (
        private val httpStatus: HttpStatus,
        private val code: String,
        private val message: String
) : ErrorCode
{
    // User
    NICKNAME_EXIST(HttpStatus.CONFLICT, "U002", "Nickname Already Exist"),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "U003", "User Not Found"),

    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", " Invalid Input Value"),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "C002", "Access Denied"),
    INVALID_URL(HttpStatus.NOT_FOUND, "C003", "URL NOT FOUND"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C004", " Invalid Input Value"),
    REQUEST_NOT_NUMBER(HttpStatus.BAD_REQUEST, "C005", "Param or PathVariable Must Number"),
    END_OF_PAGE(HttpStatus.NO_CONTENT, "C006", "End Of Page"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C007", "SERVER ERROR"),
    SECURITY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C008", "SECURITY ERROR"),
    NO_CONTENT_ERROR(HttpStatus.NO_CONTENT, "C009", "NO Content"),

    // JWT
    JWT_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "J001", "Jwt Expired"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "J002", "TOKEN UNAUTHORIZED"),
    JWT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "J003", "JWT ERROR"),
    FEIGN_CONNECT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "J004", "Feign Connect Error");

    override fun getHttpStatus(): HttpStatus {
        return httpStatus
    }

    override fun getMessage(): String {
        return message
    }

    override fun getCode(): String {
        return code
    }
}