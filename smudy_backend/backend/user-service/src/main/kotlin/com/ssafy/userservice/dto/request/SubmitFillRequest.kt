package com.ssafy.userservice.dto.request

data class SubmitFillRequest(
        val songId: String,
        val userWords: List<Word>
)
