package com.ssafy.studyservice.service

import com.ssafy.studyservice.config.ObjectMapperConfig
import com.ssafy.studyservice.dto.request.ai.SimilarityRequest
import com.ssafy.studyservice.dto.response.ai.SimilarityResponse
import com.ssafy.studyservice.service.feign.AIFeignClient
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URI

@Service
class AiService (
        @Value("\${ai.server}")
        private val AI_SERVER_URL: String,
        private val aiFeignClient: AIFeignClient,
){
        private val logger = KotlinLogging.logger {  }
        fun getSimilarity(request: SimilarityRequest) : SimilarityResponse {
                val baseUrl = URI.create(AI_SERVER_URL)
                logger.info {"request : $request"}
                val response = aiFeignClient.getSimilarity(baseUrl, request)
                logger.info {"response : $response"}
                return response
//                val mapper = ObjectMapperConfig().getObjectMapper()
//                return mapper.readValue(response, SimilarityResponse::class.java)
        }

}