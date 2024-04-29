package com.ssafy.studyservice.dto.request

data class SubmitExpressRequest(
        val problemBoxId: Int,
        val userExpresses: List<UserExpress>
)
