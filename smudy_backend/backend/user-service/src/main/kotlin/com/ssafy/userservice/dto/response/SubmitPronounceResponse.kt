package com.ssafy.userservice.dto.response

data class SubmitPronounceResponse (
        val lyricSentenceEn: String,
        val lyricSentenceKo: String,
        val userLyricSttEn: String,
        val lyricAiAnalyze: String
)