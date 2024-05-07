package com.ssafy.userservice.dto.response

data class LearnReportResponse(
        val learnReportId: Int,
        val albumJacket: String,
        val songName: String,
        val songArtist: String,
        val problemType: String,
        val learnReportDate: Long
)
