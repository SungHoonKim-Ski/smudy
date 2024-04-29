package com.ssafy.backend_dummy.study_service.dto.request

data class SubmitPickRequest(
        val problemBoxId: Int,
        val userPicks: List<UserPick>
)
