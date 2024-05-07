package com.ssafy.studyservice.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ssafy.studyservice.dto.response.ExpressResponse
import com.ssafy.studyservice.dto.request.*
import com.ssafy.studyservice.dto.response.ai.LyricAiAnalyze
import com.ssafy.studyservice.dto.response.*
import com.ssafy.studyservice.service.JwtService
import com.ssafy.studyservice.service.ProblemService
import com.ssafy.studyservice.util.CommonResult
import com.ssafy.studyservice.util.SingleResult
import com.ssafy.studyservice.util.StudyResponseService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/study")
class StudyController (
        private val responseService: StudyResponseService,
        private val problemService: ProblemService,
){
    private val logger = KotlinLogging.logger{ }
    @GetMapping("/test")
    fun signup(): ResponseEntity<SingleResult<String>> {
        val data = "study"
        logger.debug { "Hello! $data" }
        return ResponseEntity.ok(responseService.getSuccessSingleResult("데이터","성공"))
    }

    @GetMapping("/daily")
    fun dailyLyric(): ResponseEntity<SingleResult<DailyLyricResponse>> {
        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                        DailyLyricResponse(
                                dailyLyricsEn = "Are you kidding me?",
                                dailyLyricsKo = "마 장난치나!"
                        )
                        ,"오늘의 한 문장 조회 성공")
        )
    }

    @GetMapping("/fill/{songId}")
    fun getFillQuiz(@PathVariable("songId") songId: String): ResponseEntity<SingleResult<FillResponse>> {
        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                        problemService.getFillQuiz(songId)
                        ,"Fill 문제 목록 조회 성공"
                )
        )
    }

    @GetMapping("/pick/{songId}")
    fun getPickQuiz(@PathVariable("songId") songId: String): ResponseEntity<SingleResult<PickResponse>>  {
        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                        problemService.getPickQuiz(songId)
                        ,"Pick 문제 목록 조회 성공"
                )
        )
    }


    @GetMapping("/express/{songId}")
    fun getExpressQuiz(@PathVariable("songId") songId: String): ResponseEntity<SingleResult<ExpressResponse>> {

        logger.debug { "/express/$songId" }

        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                        problemService.getExpressQuiz(songId)
                        ,"Express 문제 목록 조회 성공"
                )
        )
    }

    /**
     * 1문제 채점하는 함수
     */
    @PostMapping("/express/check")
    fun checkExpress(@RequestBody request: ExpressCheckRequest): ResponseEntity<SingleResult<ExpressCheckResponse>> {
        logger.debug { "/express/check $request" }
        val response = ExpressCheckResponse(
                lyricSentenceEn = "Now I'm trying to get back",
                lyricSentenceKo = "지금은 다시 돌아가려 애쓰고 있어요",
                userLyricSentenceEn =  "I  plan to try to return in the future.",
                userLyricSentenceKo = "나는 미래에 돌아가려고 노력할 예정이에요",
                suggestLyricSentence = "I am trying to go back now." ,
                score = 90
        )
        return ResponseEntity.ok(responseService.getSuccessSingleResult(response, "Express 문제 채점 성공"))
    }

    @GetMapping("/pronounce/{songId}")
    fun getPronounceQuiz(@PathVariable("songId") songId: String): ResponseEntity<SingleResult<PronounceResponse>> {
        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                        problemService.getPronounceQuiz(songId)
                        ,"Pronounce 문제 목록 조회 성공"
                )
        )
    }

}