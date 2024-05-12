package com.ssafy.presentation.model.pronounce

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GradedPronounce (
    val userPronounce:String,
    val ttsPronounce:String,
    val lyricSentenceEn:String,
    val lyricSentenceKo:String,
    val userLyricSTT:String,
    val lyricAiAnalyze: LyricAiAnalyze
):Parcelable