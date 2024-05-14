package com.ssafy.userservice.dto.response

import com.ssafy.userservice.dto.response.ai.PronounceAnalyzeResponse

data class SubmitPronounceResponse (
        val lyricSentenceEn: String,
        val lyricSentenceKo: String,
        val userLyricSttEn: String,
        val lyricAiAnalyze: PronounceAnalyzeResponse
)