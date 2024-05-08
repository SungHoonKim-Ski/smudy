package com.ssafy.userservice.exception.error

import org.springframework.http.HttpStatus

interface ErrorCode {
    fun getHttpStatus(): HttpStatus
    fun getMessage(): String

    fun getCode(): String
}