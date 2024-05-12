package com.ssafy.data.model.music.pronounce

data class GradedPronounceResponse (
    val userPronounce:String,
    val ttsPronounce:String,
    val lyricSentenceEn:String,
    val userLyricSTT:String,
    val lyricAiAnalyze: LyricAiAnalyzeResponse
)