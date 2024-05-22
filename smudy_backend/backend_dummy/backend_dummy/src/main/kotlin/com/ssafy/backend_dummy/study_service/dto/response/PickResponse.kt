package com.ssafy.backend_dummy.study_service.dto.response

data class PickResponse(
        val problemBoxId: Int,
        val songArtist: String,
        val songId: String,
        val songName: String,
        val albumJacket: String,
        val problemResponses: MutableList<Problem> = mutableListOf()

)
