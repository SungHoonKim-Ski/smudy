package com.ssafy.userservice.service

import com.ssafy.userservice.dto.response.ai.LyricAiAnalyze
import com.ssafy.userservice.dto.response.ai.PronounceAnalyzeResponse
import com.ssafy.userservice.service.feign.AIFeignClient
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.net.URI

@Service
class AiService (
        @Value("\${ai.server}")
        private val AI_SERVER_URL: String,
        private val aiFeignClient: AIFeignClient,
){
        private val logger = KotlinLogging.logger {  }
        fun getPronounce(
                userFile: MultipartFile,
                ttsFile: MultipartFile
        ): LyricAiAnalyze {
                val baseUrl = URI.create(AI_SERVER_URL)
                return aiFeignClient.analyzeUserAndTTS(
                                baseurl = baseUrl,
                                userPronounce = userFile,
                                ttsPronounce = ttsFile
                ).result
        }
}