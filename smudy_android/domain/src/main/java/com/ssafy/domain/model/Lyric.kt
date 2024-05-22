package com.ssafy.domain.model

data class Lyric(
    val lyricEn: String,
    val lyricKo: String
){
    constructor():this("","")
}
