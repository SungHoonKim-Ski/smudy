package com.ssafy.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParcelableSubmitBlankResult(
    val lyricBlank: String,
    val originWord: String,
    val userWord: String,
    val isCorrect: Boolean
): Parcelable