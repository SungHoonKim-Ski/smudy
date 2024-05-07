package com.ssafy.userservice.service.feign

import com.ssafy.userservice.dto.gpt.ChatCompletionResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
@FeignClient(name = "openai", url = "https://api.openai.com/v1")
interface OpenAIClient {

    @PostMapping("/chat/completions")
    fun generateText(
            @RequestHeader("Authorization") authorization: String,
            @RequestBody body: Map<String, Any>
    ): String
}