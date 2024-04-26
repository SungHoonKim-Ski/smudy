package com.ssafy.backend_dummy.study_service.controller

import com.ssafy.backend_dummy.study_service.util.StudyResponseService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/study")
class StudyController (
        private val responseService: StudyResponseService
){
    private val logger = KotlinLogging.logger{ }
    @PostMapping("/test")
    fun signup(): ResponseEntity<Any> {
        var data = "study"
        logger.debug { "Hello! $data" }
        return ResponseEntity.ok(responseService.getSuccessSingleResult("데이터터터","성공"))
    }
}