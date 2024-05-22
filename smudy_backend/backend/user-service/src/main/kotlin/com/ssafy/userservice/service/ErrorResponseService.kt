package com.ssafy.userservice.service

import com.ssafy.userservice.exception.error.ErrorCode
import com.ssafy.userservice.exception.error.ErrorResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ErrorResponseService {

    private val logger = KotlinLogging.logger { }
    fun getErrorResponse(
            errorCode: ErrorCode, e: Exception
    ): ResponseEntity<ErrorResponse> {
        logger.error{"handle : ${e.message}"}
        val response = ErrorResponse(errorCode, e.message ?: "INTERNAL_SERVER_ERROR_NON_MESSAGE")
        return ResponseEntity(response, errorCode.getHttpStatus())
    }
}