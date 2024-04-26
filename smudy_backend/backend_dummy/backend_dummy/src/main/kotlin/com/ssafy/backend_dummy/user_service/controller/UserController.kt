package com.ssafy.backend_dummy.user_service.controller

import com.ssafy.backend_dummy.user_service.util.UserResponseService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController (
        private val responseService: UserResponseService
){
    private val logger = KotlinLogging.logger{ }
    @PostMapping("/test")
    fun signup(): ResponseEntity<Any> {
        var data = "user"
        logger.debug { "Hello! $data" }
        return ResponseEntity.ok(responseService.getSuccessSingleResult("데이터터터","성공"))
    }
}