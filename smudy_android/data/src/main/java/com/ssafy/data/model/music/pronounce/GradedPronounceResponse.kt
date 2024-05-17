package com.ssafy.data.model.music.pronounce

data class GradedPronounceResponse (
    val lyricSentenceEn:String,
    val lyricSentenceKo:String,
    val userLyricSttEn:String,
    val lyricAiAnalyze: LyricAiAnalyzeResponse
)