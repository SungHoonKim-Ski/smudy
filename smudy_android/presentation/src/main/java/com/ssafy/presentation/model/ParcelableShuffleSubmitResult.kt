package com.ssafy.presentation.model

import android.os.Parcelable
import com.ssafy.domain.model.ShuffleSubmitResult
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParcelableShuffleSubmitResult(
    val title: String,
    val artist: String,
    val jacket: String,
    val totalSize: Int,
    val score: Int,
    val correct: List<ParcelableShuffleQuestionProblem>,
    val wrong: List<ParcelableShuffleQuestionProblem>
): Parcelable

