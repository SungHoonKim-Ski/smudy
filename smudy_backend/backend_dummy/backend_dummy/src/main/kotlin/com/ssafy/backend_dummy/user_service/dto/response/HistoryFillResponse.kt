package com.ssafy.backend_dummy.user_service.dto.response

data class HistoryFillResponse (
        val totalSize: Int,
        val score: Int,
        val result: MutableList<FillResult> = mutableListOf()
)
