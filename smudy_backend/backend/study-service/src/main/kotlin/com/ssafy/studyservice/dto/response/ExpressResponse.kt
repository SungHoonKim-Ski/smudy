package com.ssafy.studyservice.dto.response

data class ExpressResponse(
        val problemBoxId: Int,
        val songArtist: String,
        val songId: String,
        val songName: String,
        val albumJacket: String,
        val problemResponses: List<ProblemResponse>
)
