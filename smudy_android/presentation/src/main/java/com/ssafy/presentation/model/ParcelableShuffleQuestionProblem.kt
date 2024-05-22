package com.ssafy.presentation.model

import android.os.Parcelable
import com.ssafy.domain.model.ShuffleQuestionProblem
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParcelableShuffleQuestionProblem(
    val lyricSentenceEn: String,
    val lyricSentenceKo: String,
    val userLyricSentence: String
):Parcelable

fun ShuffleQuestionProblem.toParcelable()
= ParcelableShuffleQuestionProblem(
    lyricSentenceEn, lyricSentenceKo, userLyricSentence
)




