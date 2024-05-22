package com.ssafy.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParcelableSubmitFillBlankData(
    val totalSize: Int,
    val score: Int,
    val result: List<ParcelableSubmitBlankResult>
) : Parcelable