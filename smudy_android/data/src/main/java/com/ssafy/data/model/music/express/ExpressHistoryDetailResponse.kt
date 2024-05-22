package com.ssafy.data.model.music.express

data class ExpressHistoryDetailResponse (
    val lyricSentenceEn:String,
    val lyricSentenceKo:String,
    val userLyricSentenceKo:String,
    val userLyricSentenceEn:String,
    val suggestLyricSentence:String,
    val score:Int
)