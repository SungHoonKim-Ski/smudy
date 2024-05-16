package com.ssafy.studyservice.service

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonMappingException
import com.ssafy.studyservice.config.ObjectMapperConfig
import com.ssafy.studyservice.dto.request.ExpressCheckRequest
import com.ssafy.studyservice.dto.request.ai.SimilarityRequest
import com.ssafy.studyservice.dto.response.ExpressCheckResponse
import com.ssafy.studyservice.dto.response.gpt.ChatCompletionResponse
import com.ssafy.studyservice.exception.exception.GptConnectException
import com.ssafy.studyservice.service.feign.OpenAIClient
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class OpenAIService(
        private val openAIClient: OpenAIClient,
        private val aiService: AiService,
) {

    @Value("\${openAI.key}")
    lateinit var apiKey: String

    private val logger = KotlinLogging.logger {  }

    fun markingUserAnswer(request: ExpressCheckRequest): ExpressCheckResponse {
        val lyricEn = lyricKoToEn(request.lyricSentenceKo)
        val userKo = translateLyric(request.userLyricSentence)
        val score = scoreLyric(lyricEn, request.userLyricSentence)

        return ExpressCheckResponse(
                lyricSentenceKo = request.lyricSentenceKo,
                lyricSentenceEn = request.lyricSentenceEn,
                userLyricSentenceKo = userKo,
                userLyricSentenceEn = request.userLyricSentence,
                suggestLyricSentence = lyricEn,
                score = score.toInt(),
        )
    }


    /**
     * 한국어로 직역된 노래 가사를 다시 영어로 변환해 주는 함수
     *
     * 이를 이용해 특정 가사의 한 문장의 해석을 보고
     * 유저가 작성한 문장과 GPT가 작성해준 문장간의 유사도를 비교해야 함
     */
    fun lyricKoToEn(lyricKo: String): String {
        val mapper = ObjectMapperConfig().getObjectMapper()

        val request = mapOf(
                "model" to "gpt-4",
                "messages" to arrayOf(
                        mapOf(
                                "role" to "system",
                                "content" to "An inexperienced friend has made a common mistake by translating an English song lyric directly into Korean, without considering the cultural and emotional nuances. This literal translation has altered the original's poetic impact. Your task is to take this awkward Korean version and craft new English lyrics that not only correct the error but also retain the original sentiment and artistic flavor of the song.Do not include any additional explanations, only the answer sentence.",
                        ),
                        mapOf(
                                "role" to "user",
                                "content" to "here is lyrics translated by koren $lyricKo"
                        )
                )
        )

        val response = openAIClient.generateText("Bearer $apiKey", request)

        return mapper.readValue(response, ChatCompletionResponse::class.java).choices[0].message.content
    }

    fun translateLyric(lyric: String): String {
        val mapper = ObjectMapperConfig().getObjectMapper()

        val request = mapOf(
                "model" to "gpt-4",
                "messages" to arrayOf(
                        mapOf(
                                "role" to "system",
                                "content" to "You are a lyricist with 10 years of experience. When you receive a line of lyrics written in English, your task is to translate it into Korean while preserving its emotional impact and artistic nuances as much as possible. You are skilled at maintaining the original's essence and ensuring that the translated Korean lyrics resonate deeply with the local audience.Do not include any additional explanations, only the answer sentence.",
                                "role" to "user",
                                "content" to "Translate these English lyrics into Korean for me. Use casual language, not formal. $lyric"
                        )
                )
        )

        val response = openAIClient.generateText("Bearer $apiKey", request)

        return mapper.readValue(response, ChatCompletionResponse::class.java).choices[0].message.content
    }
    fun scoreLyric(lyricEn: String, userEn: String): Int {
        val response = aiService.getSimilarity(
                SimilarityRequest(
                        sentence1 = lyricEn,
                        sentence2 = userEn
                )
        )
        logger.info { "AI server response : $response" }
        return (response.similarity.cosineSimilarity * 100).toInt()
    }

//    fun translateAllProblems() {
//        val mapper = jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//        val totalProblems = problemRepository.count()
//        val pageSize = 10
//        val pageCount = (totalProblems + pageSize - 1) / pageSize
//
//        for (i in 0 until  pageCount) {
//            transactionTemplate.execute {
//                val pageable = PageRequest.of(i.toInt(), pageSize)
//                val problemPage = problemRepository.findAll(pageable)
//                if (!problemPage.hasContent()) return@execute
//                translatePage(problemPage.toList(), mapper)
//            }
//        }
//    }
//
//    @Transactional
//    fun translatePage(problemPage: List<Problem>, mapper: ObjectMapper) {
//        problemPage.forEach loop@{ problem ->
//            if (problem.sentenceKo.isNotBlank()) return@loop
//            val lyricEn = problem.sentenceEn
//            try {
//                val response = translateLyric(lyricEn)
//                val lyricKo = mapper.readValue(response, ChatCompletionResponse::class.java).choices[0].message.content
//                problem.changesSentenceKo(lyricKo)
//                logger.info { "EN : ${problem.sentenceEn}\n KO : ${problem.sentenceKo}" }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    @Transactional
//    fun removeNotExistInPostgre(): Int {
//        val savedData = problemBoxRepository.findAllDistinctBySongId()
//        val deleteCnt =songRepository.findAllBySpotifyIdNotIn(savedData).size
//        return deleteCnt
//    }
}