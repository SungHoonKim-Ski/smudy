package com.ssafy.userservice.dto.response

import com.ssafy.userservice.db.postgre.entity.ai.EntityLyricAiAnalyze
import com.ssafy.userservice.dto.response.ai.PronounceAnalyzeResponse

data class HistoryPronounceResponse (
        val lyricSentenceEn: String,
        val lyricSentenceKo: String,
        val userLyricEn: String,
        val lyricAiAnalyze: PronounceAnalyzeResponse
)