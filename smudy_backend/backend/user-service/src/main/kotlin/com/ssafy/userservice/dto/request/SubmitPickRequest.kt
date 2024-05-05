package com.ssafy.userservice.dto.request

data class SubmitPickRequest(
        val problemBoxId: Int,
        val songId: String,
        val userPicks: List<UserPick>
)
