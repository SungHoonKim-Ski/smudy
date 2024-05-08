package com.ssafy.studyservice.controller

import com.ssafy.studyservice.dto.request.ExpressCheckRequest
import com.ssafy.studyservice.dto.response.ExpressCheckResponse
import com.ssafy.studyservice.dto.response.TranslateResponse
import com.ssafy.studyservice.service.OpenAIService
import com.ssafy.studyservice.util.SingleResult
import com.ssafy.studyservice.util.StudyResponseService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/study")
class GptController(
        private val openAIService: OpenAIService,
        private val responseService: StudyResponseService
) {

    private val logger = KotlinLogging.logger {  }
    @GetMapping("/translate")
    fun translateLyric(@RequestParam("lyric") lyric: String): ResponseEntity<SingleResult<TranslateResponse>> {
        logger.debug { "/translate! $lyric" }
        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                    TranslateResponse(
                            lyricKo = openAIService.translateLyric(lyric)
                    )
                    ,"번역 성공"
                )
        )
    }

    /**
     * 1문제 채점하는 함수
     */
    @PostMapping("/express/check")
    fun checkExpress(@RequestBody request: ExpressCheckRequest): ResponseEntity<SingleResult<ExpressCheckResponse>> {
        logger.debug { "/express/check $request" }
//        val response = ExpressCheckResponse(
//                lyricSentenceEn = "Now I'm trying to get back",
//                lyricSentenceKo = "지금은 다시 돌아가려 애쓰고 있어요",
//                userLyricSentenceEn =  "I  plan to try to return in the future.",
//                userLyricSentenceKo = "나는 미래에 돌아가려고 노력할 예정이에요",
//                suggestLyricSentence = "I am trying to go back now." ,
//                score = 90
//        )
        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                        openAIService.markingUserAnswer(request)
                        , "Express 문제 채점 성공"
                )
        )
    }

//    @GetMapping("/translateall")
//    fun translateAll() {
//        openAIService.translateAllProblems()
//    }

}