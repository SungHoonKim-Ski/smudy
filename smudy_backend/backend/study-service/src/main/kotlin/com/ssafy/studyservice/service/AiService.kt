package com.ssafy.studyservice.service

import com.ssafy.studyservice.dto.request.ai.SimilarityRequest
import com.ssafy.studyservice.service.feign.AIFeignClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URI

@Service
class AiService (
        @Value("\${ai.server}")
        private val AI_SERVER_URL: String,
        private val aiFeignClient: AIFeignClient,
){

        fun getSimilarity(request: SimilarityRequest) : String {
                val baseUrl = URI.create(AI_SERVER_URL)
                return aiFeignClient.getSimilarity(baseUrl, request)
        }


}