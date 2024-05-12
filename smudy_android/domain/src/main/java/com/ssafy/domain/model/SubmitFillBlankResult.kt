package com.ssafy.domain.model

data class SubmitFillBlankResult(
    val lyricBlank: String,
    val originWord: String,
    val userWord: String,
    val isCorrect: Boolean
)