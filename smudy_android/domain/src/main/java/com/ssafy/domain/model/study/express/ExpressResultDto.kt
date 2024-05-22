package com.ssafy.domain.model.study.express

data class ExpressResultDto (
    val suggestLyricSentence:String,
    val userAnswerSentenceEn:String,
    val userAnswerSentenceKo:String,
    val score:Int
)