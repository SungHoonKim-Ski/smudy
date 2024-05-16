package com.ssafy.data.model.music.express

data class GradedExpressResultRequest(
    val suggestLyricSentence:String,
    val userLyricSentenceEn:String,
    val userLyricSentenceKo:String,
    val score:Int
)