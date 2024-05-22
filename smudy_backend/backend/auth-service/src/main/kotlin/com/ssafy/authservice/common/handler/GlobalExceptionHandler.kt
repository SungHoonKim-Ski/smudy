package com.ssafy.authservice.common.handler

import com.ssafy.authservice.common.exception.CustomException
import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.security.SignatureException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = KotlinLogging.logger{ }

    private fun buildResponseEntity(code: Int, message: String, status: HttpStatus): ResponseEntity<Any> {
        val response: MutableMap<String, Any> = HashMap()
        response["code"] = code
        response["message"] = message

        return ResponseEntity.status(status).body(response)
    }

    // 핸들링 가능한 오류에 대한 Exception
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<*> {
        logger.error {"handleCustomException throw Exception : $e.message"}
        return buildResponseEntity(e.code, e.message, e.status)
    }

    @ExceptionHandler(ExpiredJwtException::class)
    fun handleJwtException(e: ExpiredJwtException): ResponseEntity<*> {
        logger.error {"handleJwtException throw Exception : $e.message"}
        return buildResponseEntity(403, "토큰의 유효기간이 만료되었습니다.", HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(SignatureException::class)
    fun handleJwtException(e: SignatureException): ResponseEntity<*> {
        logger.error {"handleJwtException throw Exception : $e.message"}
        return buildResponseEntity(403, "토큰의 서명이 일치하지 않습니다.", HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(Exception::class)
    fun handleServerException(e: Exception): ResponseEntity<Any> {
        logger.error {"unhandledException throw Exception : $e.message"}
        e.printStackTrace()
        return buildResponseEntity(500, "예상치 못한 에러가 발생하였습니다.<br>" + e.message, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}