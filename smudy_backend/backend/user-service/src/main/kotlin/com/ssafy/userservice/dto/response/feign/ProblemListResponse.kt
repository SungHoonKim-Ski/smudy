package com.ssafy.userservice.dto.response.feign

import com.ssafy.userservice.db.feign.Problem

data class ProblemListResponse(
        val problems: List<Problem>
)
