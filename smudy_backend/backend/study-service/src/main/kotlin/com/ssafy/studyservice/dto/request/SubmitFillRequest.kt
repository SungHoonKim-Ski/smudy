package com.ssafy.studyservice.dto.request

data class SubmitFillRequest(
        val songId: String,
        val userWords: List<Word>
)
