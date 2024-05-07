package com.ssafy.userservice.dto.response.feign

import com.ssafy.userservice.dto.response.ProblemResponse

data class ProblemListResponse(
        val problems: List<ProblemResponse>
)
