package com.ssafy.userservice.dto.gpt

data class Usage(
        val promptTokens: Int,
        val completionTokens: Int,
        val totalTokens: Int
)