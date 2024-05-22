package com.ssafy.presentation.model.pronounce

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GradedPronounce (
    val lyricSentenceEn:String,
    val lyricSentenceKo:String,
    val userLyricSttEn:String,
    val lyricAiAnalyze: LyricAiAnalyze
):Parcelable