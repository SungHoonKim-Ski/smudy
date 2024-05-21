package com.ssafy.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParcelableSubmitResult(
    val title: String,
    val artist: String,
    val jacket: String,
    val result: ParcelableSubmitFillBlankData
): Parcelable
