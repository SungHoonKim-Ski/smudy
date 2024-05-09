package com.ssafy.searchservice.common.handler

import co.elastic.clients.elasticsearch._types.ElasticsearchException
import io.github.oshai.kotlinlogging.KotlinLogging
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
    @ExceptionHandler(ElasticsearchException::class)
    fun handleCustomException(e: ElasticsearchException): ResponseEntity<*> {
        logger.error {"handleCustomException throw Exception : $e.message"}
        return buildResponseEntity(404, "검색 과정에서 에러가 발생하였습니다.", HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Exception::class)
    fun handleServerException(e: Exception): ResponseEntity<Any> {
        logger.error {"unhandledException throw Exception : $e.message"}
        return buildResponseEntity(500, "예상치 못한 에러가 발생하였습니다.<br>" + e.message, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}