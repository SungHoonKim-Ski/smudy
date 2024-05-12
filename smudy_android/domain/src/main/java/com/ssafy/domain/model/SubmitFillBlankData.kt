package com.ssafy.domain.model

data class SubmitFillBlankData(
    val totalSize: Int,
    val score: Int,
    val result: List<SubmitFillBlankResult>
)