package com.ssafy.backend_dummy.study_service.dto.request

data class UserExpress (
        val userLyricSentenceKo: String,
        val userLyricSentenceEn: String,
        val suggestLyricSentence: String,
        val score: Int
)