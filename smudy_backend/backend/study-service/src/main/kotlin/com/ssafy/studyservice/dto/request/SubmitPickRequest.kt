package com.ssafy.studyservice.dto.request

data class SubmitPickRequest(
        val problemBoxId: Int,
        val userPicks: List<UserPick>
)
