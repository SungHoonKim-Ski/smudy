package com.ssafy.userservice.dto.response

data class FillResult (
        val lyricBlank: String,
        val originWord: String,
        val userWord: String,
        val isCorrect: Boolean
)
