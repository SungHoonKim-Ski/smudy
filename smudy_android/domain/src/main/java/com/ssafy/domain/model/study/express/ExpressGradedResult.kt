package com.ssafy.domain.model.study.express

data class ExpressGradedResult(
    val lyricSentenceEn:String,
    val lyricSentenceKo:String,
    val userLyricSentenceEn:String,
    val userLyricSentenceKo:String,
    val suggestLyricSentence:String,
    val score: Int
)
