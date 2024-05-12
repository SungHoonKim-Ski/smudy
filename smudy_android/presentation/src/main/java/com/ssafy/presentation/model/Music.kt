package com.ssafy.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Music(
    val title: String,
    val artist: String,
    val jacket: String,
):Parcelable
