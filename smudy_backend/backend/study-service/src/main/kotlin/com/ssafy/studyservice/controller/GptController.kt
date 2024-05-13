package com.ssafy.studyservice.controller

import com.ssafy.studyservice.dto.request.ExpressCheckRequest
import com.ssafy.studyservice.dto.response.ExpressCheckResponse
import com.ssafy.studyservice.dto.response.TranslateResponse
import com.ssafy.studyservice.service.AiService
import com.ssafy.studyservice.service.OpenAIService
import com.ssafy.studyservice.service.feign.AIFeignClient
import com.ssafy.studyservice.util.SingleResult
import com.ssafy.studyservice.util.StudyResponseService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/study")
class GptController(
        private val openAIService: OpenAIService,
        private val responseService: StudyResponseService,
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
        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                        openAIService.markingUserAnswer(request)
                        , "Express 문제 채점 성공"
                )
        )
    }

}