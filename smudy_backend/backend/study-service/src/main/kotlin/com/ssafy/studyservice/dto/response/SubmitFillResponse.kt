package com.ssafy.studyservice.dto.response

data class SubmitFillResponse (
        val totalSize: Int,
        val score: Int,
        val result: MutableList<FillResult> = mutableListOf()
)
