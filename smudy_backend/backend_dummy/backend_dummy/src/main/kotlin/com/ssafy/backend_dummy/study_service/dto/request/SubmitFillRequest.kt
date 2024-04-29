package com.ssafy.backend_dummy.study_service.dto.request

data class SubmitFillRequest(
        val songId: String,
        val userWords: List<Word>
)
