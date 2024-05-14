package com.ssafy.presentation.model.pronounce

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FormantsAvg(
    val f1: List<Double>,
    val f2: List<Double>
):Parcelable