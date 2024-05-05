package com.ssafy.userservice.dto.response.feign

import com.ssafy.userservice.dto.response.ProblemResponse

data class PickResponse(
        val problemBoxId: Int,
        val songArtist: String,
        val songId: String,
        val songName: String,
        val albumJacket: String,
        val problems: List<ProblemResponse>
)
