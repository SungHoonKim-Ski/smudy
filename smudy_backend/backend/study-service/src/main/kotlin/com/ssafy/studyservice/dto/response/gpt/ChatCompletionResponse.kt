package com.ssafy.studyservice.dto.gpt

data class ChatCompletionResponse(
        val choices: List<Choice>,
        val created: Long,
        val id: String,
        val model: String,
        val `object`: String,
        val usage: Usage
)