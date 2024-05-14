package com.ssafy.presentation.model.pronounce

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Timestamp(
    val time: Double,
    val duration:Double,
    val word: String
): Parcelable