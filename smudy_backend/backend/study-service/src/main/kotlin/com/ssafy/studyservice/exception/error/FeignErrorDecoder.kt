package com.ssafy.studyservice.exception.error

import feign.Response
import feign.codec.ErrorDecoder
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

//@Component
class FeignErrorDecoder {
//class FeignErrorDecoder : ErrorDecoder {
//
//    private val logger = KotlinLogging.logger {  }
//    override fun decode(methodKey: String?, response: Response): Exception {
//        methodKey?.let {
//            when (response.status()) {
//                400 -> {}
//                404 -> if (methodKey.contains("getOreders")) return ResponseStatusException(HttpStatus.valueOf(response.status()), "exception")
//                500 -> if (methodKey.contains("completions")) return ResponseStatusException(HttpStatus.valueOf(response.status()), response.reason())
//                else -> return Exception(response.reason())
//            }
//        }
//
//        return Exception(response.reason())
//    }
}