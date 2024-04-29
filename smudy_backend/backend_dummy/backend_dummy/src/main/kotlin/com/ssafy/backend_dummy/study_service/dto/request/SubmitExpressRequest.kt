package com.ssafy.backend_dummy.study_service.dto.request

data class SubmitExpressRequest(
        val problemBoxId: Int,
        val userExpresses: List<UserExpress>
)
