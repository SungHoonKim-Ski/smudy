package com.ssafy.userservice.config

import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class FeignErrorDecoder : ErrorDecoder {
    override fun decode(methodKey: String, response: Response): Exception? {
        when (response.status()) {
            400 -> {}
            404 -> if (methodKey.contains("getOreders")) return ResponseStatusException(HttpStatus.valueOf(response.status()), "exception")
            else -> return Exception(response.reason())
        }
        return null
    }
}