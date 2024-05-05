package com.ssafy.userservice.dto.response

data class SubmitFillResponse (
        val totalSize: Int,
        val score: Int,
        val result: List<FillResult>
)
