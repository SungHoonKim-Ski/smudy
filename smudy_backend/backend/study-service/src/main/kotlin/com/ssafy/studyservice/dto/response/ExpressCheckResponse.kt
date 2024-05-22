package com.ssafy.studyservice.dto.response

data class ExpressCheckResponse (
        val lyricSentenceEn: String,
        val lyricSentenceKo: String,
        val userLyricSentenceKo: String,
        val userLyricSentenceEn: String,
        val suggestLyricSentence: String,
        val score: Int
)