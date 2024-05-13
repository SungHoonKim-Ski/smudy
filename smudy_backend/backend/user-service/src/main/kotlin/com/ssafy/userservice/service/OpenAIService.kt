package com.ssafy.userservice.service

import com.ssafy.userservice.config.ObjectMapperConfig
import com.ssafy.userservice.dto.gpt.ChatCompletionResponse
import com.ssafy.userservice.service.feign.OpenAIClient
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class OpenAIService(
        private val openAIClient: OpenAIClient,
) {

    @Value("\${openAI.key}")
    lateinit var apiKey: String

    private val logger = KotlinLogging.logger {  }

    fun translateLyric(lyric: String): String {
        val mapper = ObjectMapperConfig().getObjectMapper()

        val request = mapOf(
                "model" to "gpt-4",
                "messages" to arrayOf(
                        mapOf(
                                "role" to "system",
                                "content" to "You are a lyricist with 10 years of experience. When you receive a line of lyrics written in English, your task is to translate it into Korean while preserving its emotional impact and artistic nuances as much as possible. You are skilled at maintaining the original's essence and ensuring that the translated Korean lyrics resonate deeply with the local audience.",
                                "role" to "user",
                                "content" to "Translate these English lyrics into Korean for me. Use casual language, not formal. $lyric"
                        )
                )
        )

        val response = openAIClient.generateText("Bearer $apiKey", request)

        return mapper.readValue(response, ChatCompletionResponse::class.java).choices[0].message.content
    }

}