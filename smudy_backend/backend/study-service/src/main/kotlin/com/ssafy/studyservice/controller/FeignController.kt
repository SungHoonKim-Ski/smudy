package com.ssafy.studyservice.controller

import com.ssafy.studyservice.db.postgre.entity.Problem
import com.ssafy.studyservice.service.AiService
import com.ssafy.studyservice.service.ProblemService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/study/feign")
class FeignController (
        private val problemService: ProblemService,
){
    private val logger = KotlinLogging.logger{ }

    @GetMapping("/problems/{problemBoxId}")
    fun getProblemsByProblemBoxId(
            @PathVariable(value = "problemBoxId", required = true) problemBoxId: Int
    ) : List<Problem> {
        return problemService.getProblemsByProblemBoxId(problemBoxId)
    }


}