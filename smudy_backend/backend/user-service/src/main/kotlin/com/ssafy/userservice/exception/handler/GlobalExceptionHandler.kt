package com.ssafy.userservice.exception.handler

import com.ssafy.userservice.exception.error.CommonErrorCode
import com.ssafy.userservice.exception.error.ErrorResponse
import com.ssafy.userservice.exception.exception.EndOfPageException
import com.ssafy.userservice.exception.exception.HistoryNotFoundException
import com.ssafy.userservice.exception.exception.RequestNotNumberException
import com.ssafy.userservice.exception.exception.StudyListAllExistException
import com.ssafy.userservice.service.ErrorResponseService
import jakarta.ws.rs.core.NoContentException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler(
        private val errorResponseService: ErrorResponseService
) {

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
}