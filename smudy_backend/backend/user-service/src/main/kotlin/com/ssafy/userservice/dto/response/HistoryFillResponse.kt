package com.ssafy.userservice.dto.response

data class HistoryFillResponse (
        val totalSize: Int,
        val score: Int,
        val result: MutableList<FillResult> = mutableListOf()
)
