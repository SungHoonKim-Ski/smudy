package com.ssafy.backend_dummy.study_service.dto.request

data class SubmitExpressRequest(
        val problemBoxId: Int,
        val songId: String,
        val userExpresses: List<UserExpress>
)