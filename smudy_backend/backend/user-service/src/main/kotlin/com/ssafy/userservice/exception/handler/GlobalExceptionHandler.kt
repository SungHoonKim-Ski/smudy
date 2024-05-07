package com.ssafy.userservice.exception.handler

import com.ssafy.userservice.config.ObjectMapperConfig
import com.ssafy.userservice.exception.error.CommonErrorCode
import com.ssafy.userservice.exception.error.ErrorResponse
import com.ssafy.userservice.exception.exception.*
import com.ssafy.userservice.service.ErrorResponseService
import feign.FeignException
import jakarta.ws.rs.core.NoContentException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler(
        private val errorResponseService: ErrorResponseService,
        private val objectMapperConfig: ObjectMapperConfig,

) {

    @ExceptionHandler(Exception::class)
    protected fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        e.printStackTrace()
        return errorResponseService.getErrorResponse(CommonErrorCode.INTERNAL_SERVER_ERROR, e)
    }
    @ExceptionHandler(NoContentException::class)
    protected fun handleNoContentException(e: NoContentException): ResponseEntity<ErrorResponse> {
        return errorResponseService.getErrorResponse(CommonErrorCode.NO_CONTENT_ERROR, e)
    }

    @ExceptionHandler(RequestNotNumberException::class)
    protected fun handleRequestNotNumberException(e: RequestNotNumberException): ResponseEntity<ErrorResponse> {
        return errorResponseService.getErrorResponse(CommonErrorCode.REQUEST_NOT_NUMBER, e)
    }

    @ExceptionHandler(EndOfPageException::class)
    protected fun handleEndOfPageException(e: EndOfPageException): ResponseEntity<ErrorResponse> {
        return errorResponseService.getErrorResponse(CommonErrorCode.END_OF_PAGE, e)
    }

    @ExceptionHandler(StudyListAllExistException::class)
    protected fun handleStudyListAllExistException(e: StudyListAllExistException): ResponseEntity<ErrorResponse> {
        return errorResponseService.getErrorResponse(CommonErrorCode.STUDY_LIST_ALL_EXIST, e)
    }

    @ExceptionHandler(HistoryNotFoundException::class)
    protected fun handleHistoryNotFoundException(e: HistoryNotFoundException): ResponseEntity<ErrorResponse> {
        return errorResponseService.getErrorResponse(CommonErrorCode.NO_CONTENT_ERROR, e)
    }

    @ExceptionHandler(LearnReportNotFoundException::class)
    protected fun handleLearnReportNotFoundException(e: LearnReportNotFoundException): ResponseEntity<ErrorResponse> {
        return errorResponseService.getErrorResponse(CommonErrorCode.INVALID_INPUT_VALUE, e)
    }

    @ExceptionHandler(SongNotFoundException::class)
    protected fun handleSongNotFoundException(e: SongNotFoundException): ResponseEntity<ErrorResponse> {
        return errorResponseService.getErrorResponse(CommonErrorCode.INTERNAL_SERVER_ERROR, e)
    }

    @ExceptionHandler(LearnReportNotSavedException::class)
    protected fun handleLearnReportNotSavedException(e: LearnReportNotSavedException): ResponseEntity<ErrorResponse> {
        return errorResponseService.getErrorResponse(CommonErrorCode.INTERNAL_SERVER_ERROR, e)
    }

    @ExceptionHandler(FeignException::class)
    protected fun handleFeignException(e: FeignException): ResponseEntity<Any> {
        val responseJson = e.contentUTF8()
        val responseMap = objectMapperConfig.getObjectMapper().readValue(responseJson, Map::class.java)

        return ResponseEntity
                .status(e.status())
                .body(responseMap)
    }
}