package com.ssafy.backend_dummy.user_service.dto.response

data class UserExpress (
        val lyricSentenceEn: String,
        val lyricSentenceKo: String,
        val userLyricSentenceKo: String,
        val userLyricSentenceEn: String,
        val suggestLyricSentence: String,
        val score: Int
)