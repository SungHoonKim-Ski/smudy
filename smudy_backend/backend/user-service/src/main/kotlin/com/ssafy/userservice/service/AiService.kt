package com.ssafy.userservice.service

import com.ssafy.userservice.dto.request.SubmitPronounceRequest
import com.ssafy.userservice.dto.response.SubmitPronounceResponse
import com.ssafy.userservice.dto.response.ai.LyricAiAnalyze
import com.ssafy.userservice.dto.response.feign.PronounceResponse
import com.ssafy.userservice.service.feign.AIFeignClient
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile
import java.net.URI

@Service
class AiService (
        @Value("\${ai.server}")
        private val AI_SERVER_URL: String,
        private val aiFeignClient: AIFeignClient,
        private val openAIService: OpenAIService,
){
        private val logger = KotlinLogging.logger {  }
        fun getPronounce(
                userFile: MultipartFile,
                ttsFile: MultipartFile,
                request: SubmitPronounceRequest
        ) : SubmitPronounceResponse
        {
                val baseUrl = URI.create(AI_SERVER_URL)
                logger.info {"request : $request"}
                val aiResponse = aiFeignClient
                        .analyzeUserAndTTS(
                                baseurl = baseUrl,
                                userPronounce = userFile,
                                ttsPronounce = ttsFile
                        )
                logger.info {"response : $aiResponse"}

                val userTranslate = openAIService.translateLyric(aiResponse.result.userFullText)

                val response = SubmitPronounceResponse(
                        userPronounce = "https://file-examples.com/wp-content/storage/2017/04/file_example_MP4_480_1_5MG.mp4",
                        ttsPronounce = "https://file-examples.com/wp-content/storage/2017/04/file_example_MP4_480_1_5MG.mp4",
                        lyricSentenceEn = request.lyricSentenceEn,
                        lyricSentenceKo = request.lyricSentenceKo,
                        userLyricSttEn = aiResponse.result.userFullText,
                        userLyricSttKo = userTranslate,
                        lyricAiAnalyze = aiResponse.result
                )

                return response
        }

}