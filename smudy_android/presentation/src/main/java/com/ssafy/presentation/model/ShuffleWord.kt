package com.ssafy.presentation.model

data class ShuffleWord(
    val word: String,
    val origPosition: Int,
    val isVisible: Boolean = true
)
