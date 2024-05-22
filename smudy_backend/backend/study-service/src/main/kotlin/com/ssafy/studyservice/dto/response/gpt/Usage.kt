package com.ssafy.studyservice.dto.response.gpt

data class Usage(
        val promptTokens: Int,
        val completionTokens: Int,
        val totalTokens: Int
)