package com.ssafy.presentation.model

data class BlankQuestion(
    val lyricBlank: String,
    val lyricBlankAnswer: String,
    val lyricStartTimeStamp: String,
    val inputAnswer: String,
    val blankStart: Int,
    val blankEnd: Int
)

fun BlankQuestion.toQuestion(): String {
    val lyricBlank = lyricBlank
    val blankIdx = blankStart
    val lastBlankIdx = blankEnd
    var before = lyricBlank
    val input = inputAnswer
    var after = ""
    if (blankIdx != -1) {
        before = lyricBlank.substring(0, blankIdx)
        after = lyricBlank.substring(lastBlankIdx + 1, lyricBlank.lastIndex + 1)
    }
    return before + input + after
}
