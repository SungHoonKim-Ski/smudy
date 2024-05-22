package com.ssafy.studyservice.dto.response.gpt

data class Choice(
        val index: Int,
        val message: Message,
)