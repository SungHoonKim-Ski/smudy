package com.ssafy.presentation.model

data class BlankQuestion(
    val lyricBlank: String,
    val lyricBlankAnswer: String,
    val lyricStartTimeStamp: String,
    val inputAnswer: String,
    val blankStart: Int,
    val blankEnd: Int
)
