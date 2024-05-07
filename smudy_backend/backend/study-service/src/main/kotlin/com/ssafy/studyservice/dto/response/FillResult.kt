package com.ssafy.studyservice.dto.response

data class FillResult (
        val lyricBlank: String,
        val originWord: String,
        val userWord: String,
        val isCorrect: Boolean
)
