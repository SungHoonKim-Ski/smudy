package com.ssafy.domain.model.study.pronounce

data class GradedPronounce (
    val userPronounce:String,
    val ttsPronounce:String,
    val lyricSentenceEn:String,
    val lyricSentenceKo:String,
    val userLyricSttEn:String,
    val userLyricSttKo:String,
    val lyricAiAnalyze: LyricAiAnalyze
)