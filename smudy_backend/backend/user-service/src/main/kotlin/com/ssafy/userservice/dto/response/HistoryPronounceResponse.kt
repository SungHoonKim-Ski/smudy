package com.ssafy.userservice.dto.response

import com.ssafy.userservice.dto.response.ai.LyricAiAnalyze

data class HistoryPronounceResponse (
        val userPronounce: String,
        val ttsPronounce: String,
        val lyricSentenceEn: String,
        val lyricSentenceKo: String,
        val userLyricSttEn: String,
        val userLyricSttKo: String,
        val lyricAiAnalyze: LyricAiAnalyze
)