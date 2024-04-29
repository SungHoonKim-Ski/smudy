package com.ssafy.backend_dummy.user_service.dto.response

data class LearnReport(
        val learnReportId: Int,
        val albumJacket: String,
        val songName: String,
        val songArtist: String,
        val problemType: String,
        val learnReportDate: Long
)
