package com.ssafy.userservice.dto.response

import com.ssafy.userservice.dto.response.ai.LyricAiAnalyze
import com.ssafy.userservice.dto.response.ai.PronounceAnalyzeResponse

data class HistoryPronounceResponse (
        val lyricSentenceEn: String,
        val lyricSentenceKo: String,
        val userLyricEn: String,
        val lyricAiAnalyze: LyricAiAnalyze
)