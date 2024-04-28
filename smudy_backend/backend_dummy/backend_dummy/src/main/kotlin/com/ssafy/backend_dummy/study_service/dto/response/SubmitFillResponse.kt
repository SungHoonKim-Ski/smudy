package com.ssafy.backend_dummy.study_service.dto.response

data class SubmitFillResponse (
        val totalSize: Int,
        val score: Int,
        val result: MutableList<FillResult> = mutableListOf()
)
