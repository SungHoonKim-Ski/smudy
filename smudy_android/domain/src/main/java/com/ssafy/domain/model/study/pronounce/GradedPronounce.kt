package com.ssafy.domain.model.study.pronounce

data class GradedPronounce (
    val lyricSentenceEn:String,
    val lyricSentenceKo:String,
    val userLyricSttEn:String,
    val lyricAiAnalyze: LyricAiAnalyze
)