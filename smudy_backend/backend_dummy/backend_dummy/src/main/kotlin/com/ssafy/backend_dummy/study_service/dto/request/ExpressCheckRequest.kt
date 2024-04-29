package com.ssafy.backend_dummy.study_service.dto.request

data class ExpressCheckRequest (
        val lyricSentenceEn: String,
        val lyricSentenceKo: String,
        val userLyricSentence: String
)