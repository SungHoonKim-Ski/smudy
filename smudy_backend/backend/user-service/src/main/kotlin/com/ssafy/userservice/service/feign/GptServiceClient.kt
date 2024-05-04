package com.ssafy.userservice.service.feign

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "gpt-api", url = "https://api.openai.com/v1/chat")
interface GptServiceClient {

    @PostMapping("/completions")
    fun chatting(@RequestHeader("Authorization") token: String, @RequestBody request: Map<String, Any>)
}