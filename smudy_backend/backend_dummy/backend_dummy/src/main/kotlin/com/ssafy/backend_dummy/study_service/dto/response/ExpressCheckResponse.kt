package com.ssafy.backend_dummy.study_service.dto.response

data class ExpressCheckResponse (
        val lyricSentenceEn: String,
        val lyricSentenceKo: String,
        val userLyricSentenceKo: String,
        val userLyricSentenceEn: String,
        val suggestLyricSentence: String,
        val score: Int
)