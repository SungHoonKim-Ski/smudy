package com.ssafy.backend_dummy.study_service.dto.request

data class SubmitPronounceRequest (
        val lyricSentenceEn: String,
        val lyricSentenceKo: String,
        val userLyricSTT: String
)