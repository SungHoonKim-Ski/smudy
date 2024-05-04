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
                                "content" to "이 영어 가사들을 한국어로 번역해줘 존댓말 말고 반말로 해줘: $lyric"
                        )
                )
        )

        val response = openAIClient.generateText("Bearer $apiKey", request)

        return mapper.readValue(response, ChatCompletionResponse::class.java).choices[0].message.content
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