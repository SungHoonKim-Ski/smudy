package com.ssafy.userservice.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ssafy.userservice.dto.request.AddStudyListRequest
import com.ssafy.userservice.dto.response.*
import com.ssafy.userservice.dto.response.ai.LyricAiAnalyze
import com.ssafy.userservice.exception.exception.RequestNotNumberException
import com.ssafy.userservice.service.LearnReportService
import com.ssafy.userservice.service.StudyListService
import com.ssafy.userservice.service.UserService
import com.ssafy.userservice.service.WrongService
import com.ssafy.userservice.util.CommonResult
import com.ssafy.userservice.util.SingleResult
import com.ssafy.userservice.util.UserResponseService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/user")
class UserController (
        private val responseService: UserResponseService,
        private val userService: UserService,
        private val learnReportService: LearnReportService,
        private val wrongService: WrongService,
        private val studyListService: StudyListService,

){
    private val logger = KotlinLogging.logger{ }
    private val userUUID = UUID.fromString("74cb4a7c-1751-4ede-829a-de5046ef4688")

    @GetMapping("/test")
    fun signup(): ResponseEntity<SingleResult<String>> {
        val data = "user"
        logger.info { "Hello! $data" }
        return ResponseEntity.ok(responseService.getSuccessSingleResult("데이터","성공"))
    }

    @GetMapping("/info")
    fun getUserInfo(@RequestHeader("Authorization") accessToken: String): ResponseEntity<SingleResult<InfoResponse>> {

        logger.info { "/info/$accessToken" }

        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                        userService.getUserInfo(userUUID)
                        , "유저 정보 조회 완료")
        )
    }

    @GetMapping("/streak")
    fun getUserStreak(@RequestHeader("Authorization") accessToken: String): ResponseEntity<SingleResult<StreakResponse>> {

        logger.info { "/streak/$accessToken" }

        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                        learnReportService.getUserStreak(
                                userUUID
                        )
                        , "스트릭 조회 성공")
        )
    }

    @GetMapping("/wrong")
    fun getUserWrong(@RequestHeader("Authorization") accessToken: String): ResponseEntity<SingleResult<WrongLyricResponse>> {

        logger.info { "/info/$accessToken" }

        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                        wrongService.getRandomWrongSentence(userUUID)
                        , "틀린 표현 조회 성공"
                )
        )
    }

    @GetMapping("/studylist")
    fun getUserStudyList(
            @RequestHeader("Authorization") accessToken: String
            , @RequestParam(value = "page", required = true) page: String
    ): ResponseEntity<SingleResult<StudyListResponse>> {
        logger.info { "/studylist/$page" }

        page.toIntOrNull()?.let {
            val pageable: Pageable = PageRequest.of(it, 20)

            return ResponseEntity.ok(
                    responseService.getSuccessSingleResult(
                            studyListService.getUserStudyList(
                                    userUUID
                                    , pageable)
                            , "스터디 리스트 조회 성공"
                    )
            )
        } ?: throw RequestNotNumberException("StudyList의 페이지는 숫자여야 합니다.")

    }

    @PostMapping("/studylist/add")
    fun addStudyList(@RequestBody request: AddStudyListRequest) : ResponseEntity<CommonResult> {
        logger.info { "/studylist/add $request" }

        val result = studyListService.addUserStudyList(userUUID, request.songIds)

        return ResponseEntity.ok(
                responseService.getSuccessResult(
                        "이미 리스트에 존재하는 ${result[1]}개의 곡을 제외한 " +
                                "${result[0]}개의 곡을 추가했습니다"
                )
        )
    }

    @GetMapping("/history")
    fun getUserHistory(
            @RequestHeader("Authorization") accessToken: String
            , @RequestParam(value = "time", required = true) time: String
    ): ResponseEntity<SingleResult<HistoryResponse>> {

        logger.info { "/history/$time" }
        time.toLongOrNull()?.let {
            return ResponseEntity.ok(
                    responseService.getSuccessSingleResult(
                            learnReportService.getUserLearnReport(userUUID, it)
                            , "히스토리 조회 성공"
                    )
            )
        } ?: throw RequestNotNumberException("시간은 Long Type이어야 합니다")
    }

    @GetMapping("/history/fill/{learnReportId}")
    fun getUserHistoryFill(
            @RequestHeader("Authorization") accessToken: String
            , @PathVariable(value = "learnReportId", required = true) learnReportId: String
    ): ResponseEntity<SingleResult<HistoryFillResponse>>  {
        logger.info { "/history/fill $learnReportId" }
        learnReportId.toIntOrNull()?.let {
            return ResponseEntity.ok(
                    responseService.getSuccessSingleResult(
                            learnReportService.getUserFillHistory(it)
                            ,"Fill 히스토리 조회 완료"
                    )
            )
        } ?: throw RequestNotNumberException("LearnReportID는 Number 타입이어야 합니다")

    }

    @GetMapping("/history/pick/{learnReportId}")
    fun getUserHistoryPick(
            @RequestHeader("Authorization") accessToken: String
            , @PathVariable(value = "learnReportId", required = true) learnReportId: String
    ): ResponseEntity<SingleResult<HistoryPickResponse>> {
        logger.info { "/history/pick/ $learnReportId" }

        learnReportId.toIntOrNull()?.let {
            return ResponseEntity.ok(
                    responseService.getSuccessSingleResult(
                            learnReportService.getUserPickHistory(it)
                            ,"Pick 히스토리 조회 성공"
                    )
            )
        } ?: throw RequestNotNumberException("LearnReportID는 Number 타입이어야 합니다")

    }

    @GetMapping("/history/express/{learnReportId}")
    fun getUserHistoryExpress(
            @RequestHeader("Authorization") accessToken: String
            , @PathVariable(value = "learnReportId", required = true) learnReportId: String
    ): ResponseEntity<SingleResult<HistoryExpressResponse>> {
        logger.info { "/history/express/$learnReportId" }

        learnReportId.toIntOrNull()?.let {
            return ResponseEntity.ok(
                    responseService.getSuccessSingleResult(
                            learnReportService.getUserExpressHistory(it)
                            ,"Express 히스토리 조회 성공"
                    )
            )
        } ?: throw RequestNotNumberException("LearnReportID는 Number 타입이어야 합니다")
    }

    @GetMapping("/history/pronounce/{learnReportId}")
    fun getUserHistoryPronounce(
            @RequestHeader("Authorization") accessToken: String
            , @PathVariable(value = "learnReportId", required = true) learnReportId: String
    ): ResponseEntity<SingleResult<HistoryPronounceResponse>> {
        logger.info { "/history/pronounce/$learnReportId" }

        learnReportId.toIntOrNull()?.let {
            return ResponseEntity.ok(
                    responseService.getSuccessSingleResult(
                            learnReportService.getUserPronounceHistory(it)
                            ,"Pronounce 히스토리 조회 성공"
                    )
            )
        } ?: throw RequestNotNumberException("LearnReportID는 Number 타입이어야 합니다")
    }

}