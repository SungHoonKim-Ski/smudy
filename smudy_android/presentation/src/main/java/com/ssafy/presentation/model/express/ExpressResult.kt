package com.ssafy.presentation.model.express

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExpressResult(
    val suggestedSentenceEn:String,
    val suggestedSentenceKo:String,
    val userAnswerSentenceEn:String,
    val userAnswerSentenceKo:String,
    val score:Int
): Parcelable