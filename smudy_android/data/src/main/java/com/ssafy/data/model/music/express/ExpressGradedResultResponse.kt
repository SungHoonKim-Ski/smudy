package com.ssafy.data.model.music.express

data class ExpressGradedResultResponse(
    val lyricSentenceEn:String,
    val lyricSentenceKo:String,
    val userLyricSentenceEn:String,
    val userLyricSentenceKo:String,
    val suggestLyricSentence:String,
    val score: Int
)
