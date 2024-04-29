package com.ssafy.userservice.dto.response

data class LearnReport(
        val learnReportId: Int,
        val albumJacket: String,
        val songName: String,
        val songArtist: String,
        val problemType: String,
        val learnReportDate: Long
)
