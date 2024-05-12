package com.ssafy.presentation.model.pronounce

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PitchData(
    val times: List<Double>,
    val values: List<Double>
): Parcelable