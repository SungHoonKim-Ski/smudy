package com.ssafy.userservice.service.feign

import com.ssafy.userservice.dto.response.ProblemResponse
import com.ssafy.userservice.dto.response.feign.Problem
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "study-service", path = "/api/study/feign")
interface StudyServiceClient {
    @GetMapping("/problems/{problemBoxId}")
    fun getProblemsByProblemBoxId(
            @PathVariable(value = "problemBoxId", required = true) problemBoxId: Int
    ): List<Problem>

}