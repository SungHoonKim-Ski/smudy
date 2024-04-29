package com.ssafy.backend_dummy.study_service.dto.response

import com.ssafy.backend_dummy.study_service.dto.response.ai.LyricAiAnalyze

data class SubmitPronounceResponse (
        val userPronounce: String,
        val ttsPronounce: String,
        val lyricSentenceEn: String,
        val lyricSentenceKo: String,
        val userLyricSTT: String,
        val lyricAiAnalyze: LyricAiAnalyze
)