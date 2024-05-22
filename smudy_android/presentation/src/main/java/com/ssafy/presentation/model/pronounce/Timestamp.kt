package com.ssafy.presentation.model.pronounce

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Timestamp(
    val word: String,
    val startTime:Double,
    val endTime:Double
): Parcelable