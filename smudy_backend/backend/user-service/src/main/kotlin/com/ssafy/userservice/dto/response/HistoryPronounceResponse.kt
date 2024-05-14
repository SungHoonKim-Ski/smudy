package com.ssafy.userservice.dto.response

import com.ssafy.userservice.dto.response.ai.LyricAiAnalyze

data class HistoryPronounceResponse (
        val lyricSentenceEn: String,
        val lyricSentenceKo: String,
        val userLyricEn: String,
        val lyricAiAnalyze: LyricAiAnalyze
)