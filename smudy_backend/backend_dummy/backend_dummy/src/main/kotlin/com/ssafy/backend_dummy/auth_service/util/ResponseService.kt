package com.ssafy.backend_dummy.auth_service.util

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ResponseService {
//    fun getSuccessMessageResult(message: String): CommonResult {
//        return CommonResult(message, CommonResponseCode.SUCCESS.code)
//    }
//
//    // 단일건 결과를 처리하는 메소드
//    fun <T> getSuccessSingleResult(data: T, msg: String): SingleResult<T> {
//        val result: SingleResult<T> = SingleResult(data, msg)
//        result.setData(data)
//        result.setMessage(msg)
//        successResult = result
//        return result
//    }
//
//    fun <T> getSingleResult(data: T, msg: String?, code: Int): SingleResult<T> {
//        val result: SingleResult<T> = SingleResult()
//        result.setCode(code)
//        result.setMessage(msg)
//        result.setData(data)
//        return result
//    }
//
//    // 다중건 결과를 처리하는 메소드
//    fun <T> getSuccessListResult(list: List<T>?): ListResult<T> {
//        val result: ListResult<T> = ListResult()
//        result.setData(list)
//        successResult = result
//        return result
//    }
//
//    // 다중건 결과를 처리하는 메소드 + msg 세팅
//    fun <T> getSuccessListResult(list: List<T>, msg: String): ListResult<T> {
//        val result: ListResult<T> = ListResult(list)
//        result.setData(list)
//        result.setMessage(msg)
//        successResult = result
//        return result
//    }
//
//    // 성공 결과만 처리하는 메소드
//    fun getSuccessResult(msg: String): CommonResult {
//        return CommonResult(msg, CommonResponseCode.SUCCESS.code)
//    }

    private fun setSuccessResult(result: CommonResult) {
        result.code = CommonResponseCode.SUCCESS.code
        result.message = result.message
    }
    /**
     * 200
     * @return ResponseEntity(200)
     */
    fun OK(): ResponseEntity<HttpStatusCode> {
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    /**
     * 204
     * @return ResponseEntity(204)
     */
    fun NO_CONTENT(): ResponseEntity<HttpStatusCode> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    /**
     * 400 ERROR
     * @return ResponseEntity(400)
     */
    fun BAD_REQUEST(): ResponseEntity<HttpStatusCode> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }

    /**
     * 401 ERROR
     * @return ResponseEntity(401)
     */
    fun UNAUTHORIZED(): ResponseEntity<HttpStatusCode> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    }

    /**
     * 403 ERROR
     * @return ResponseEntity(403)
     */
    fun FORBIDDEN(): ResponseEntity<HttpStatusCode> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
    }

    /**
     * 404 ERROR
     * @return ResponseEntity(404)
     */
    fun NOT_FOUND(): ResponseEntity<HttpStatusCode> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }

    /**
     * 409 ERROR
     * @return ResponseEntity(409)
     */
    fun CONFLICT(): ResponseEntity<HttpStatusCode> {
        return ResponseEntity.status(HttpStatus.CONFLICT).build()
    }

    /**
     * 500 ERROR
     * @return ResponseEntity(500)
     */
    fun INTERNAL_SERVER_ERROR(): ResponseEntity<HttpStatusCode> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
    }
}