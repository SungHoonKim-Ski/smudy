package com.ssafy.backend_dummy.user_service.dto.response

data class FillResult (
        val lyricBlank: String,
        val originWord: String,
        val userWord: String,
        val isCorrect: Boolean
)
