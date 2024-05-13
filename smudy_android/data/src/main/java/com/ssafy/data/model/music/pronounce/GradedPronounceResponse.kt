package com.ssafy.data.model.music.pronounce

data class GradedPronounceResponse (
    val userPronounce:String,
    val ttsPronounce:String,
    val lyricSentenceEn:String,
    val lyricSentenceKo:String,
    val userLyricSttEn:String,
    val userLyricSttKo:String,
    val lyricAiAnalyze: LyricAiAnalyzeResponse
)