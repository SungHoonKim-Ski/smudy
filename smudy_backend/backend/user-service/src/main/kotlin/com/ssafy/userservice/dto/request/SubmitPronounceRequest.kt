package com.ssafy.userservice.dto.request

data class SubmitPronounceRequest (
        val lyricSentenceEn: String,
        val lyricSentenceKo: String,
        val songId: String,
)