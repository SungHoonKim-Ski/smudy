package com.ssafy.userservice.service.feign

import com.ssafy.userservice.db.feign.Problem
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient("study-service")
interface StudyServiceClient {
    @GetMapping("/api/study/feign/problems/{problemBoxId}")
    fun getProblemsByProblemBoxId(
            @PathVariable(value = "problemBoxId", required = true) problemBoxId: Int
    ): List<Problem>

}