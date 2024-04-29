package com.ssafy.studyservice.dto.request

data class ExpressCheckRequest (
        val lyricSentenceEn: String,
        val lyricSentenceKo: String,
        val userLyricSentence: String
)