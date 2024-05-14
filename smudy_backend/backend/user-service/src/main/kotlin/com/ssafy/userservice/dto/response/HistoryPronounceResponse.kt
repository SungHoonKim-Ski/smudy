package com.ssafy.userservice.dto.response

import com.ssafy.userservice.db.postgre.entity.ai.EntityLyricAiAnalyze

data class HistoryPronounceResponse (
        val lyricSentenceEn: String,
        val lyricSentenceKo: String,
        val userLyricEn: String,
        val lyricAiAnalyze: EntityLyricAiAnalyze
)