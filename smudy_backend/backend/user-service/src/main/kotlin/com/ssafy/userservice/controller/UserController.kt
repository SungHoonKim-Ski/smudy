package com.ssafy.userservice.controller

import com.ssafy.userservice.dto.request.*
import com.ssafy.userservice.dto.response.*
import com.ssafy.userservice.exception.exception.RequestNotNumberException
import com.ssafy.userservice.service.*
import com.ssafy.userservice.util.CommonResult
import com.ssafy.userservice.util.SingleResult
import com.ssafy.userservice.util.UserResponseService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/api/user")
class UserController (
        private val responseService: UserResponseService,
        private val userService: UserService,
        private val learnReportService: LearnReportService,
        private val wrongService: WrongService,
        private val studyListService: StudyListService,
        private val jwtService: JwtService,
        private val recommendService: RecommendService,
){
    private val logger = KotlinLogging.logger{ }

    @GetMapping("/test")
    fun signup(): ResponseEntity<SingleResult<String>> {
        val data = "user"
        logger.info { "Hello! $data" }
        return ResponseEntity.ok(responseService.getSuccessSingleResult("데이터","성공"))
    }

    @GetMapping("/info")
    fun getUserInfo(): ResponseEntity<SingleResult<InfoResponse>> {

        val userInternalId = UUID.fromString(jwtService.getUserInternalId())

        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                        userService.getUserInfo(userInternalId)
                        , "유저 정보 조회 완료")
        )
    }

    @GetMapping("/streak")
    fun getUserStreak(): ResponseEntity<SingleResult<StreakResponse>> {

        val userInternalId = UUID.fromString(jwtService.getUserInternalId())

        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                        learnReportService.getUserStreak(
                                userInternalId
                        )
                        , "스트릭 조회 성공")
        )
    }

    @GetMapping("/wrong")
    fun getUserWrong(): ResponseEntity<SingleResult<WrongLyricResponse>> {

        val userInternalId = UUID.fromString(jwtService.getUserInternalId())

        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                        wrongService.getRandomWrongSentence(userInternalId)
                        , "틀린 표현 조회 성공"
                )
        )
    }

    @GetMapping("/studylist")
    fun getUserStudyList(
        @RequestParam(value = "page", required = true) page: String
    ): ResponseEntity<SingleResult<StudyListResponse>> {

        logger.info { "/studylist/$page" }
        val userInternalId = UUID.fromString(jwtService.getUserInternalId())

        page.toIntOrNull()?.let {
            val pageable: Pageable = PageRequest.of(it, 20)

            return ResponseEntity.ok(
                    responseService.getSuccessSingleResult(
                            studyListService.getUserStudyList(
                                    userInternalId
                                    , pageable)
                            , "스터디 리스트 조회 성공"
                    )
            )
        } ?: throw RequestNotNumberException("StudyList의 페이지는 숫자여야 합니다.")

    }

    @PostMapping("/studylist/add")
    fun addStudyList(
        @RequestBody request: AddStudyListRequest
    ) : ResponseEntity<CommonResult> {

        logger.info { "/studylist/add $request" }
        val userInternalId = UUID.fromString(jwtService.getUserInternalId())
        val saveCount = studyListService.addUserStudyList(userInternalId, request.songIds)

        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                        mapOf("saveCount" to saveCount),
                        "${request.songIds.size}곡 중${saveCount}곡을 스터디리스트에 추가했습니다"
                )
        )
    }

    @DeleteMapping("/studylist")
    fun removeStudyList(
            @RequestParam(value = "songId", required = true) songId: String
    ) : ResponseEntity<CommonResult>{
        logger.info { "/delete/$songId" }
        val userInternalId = UUID.fromString(jwtService.getUserInternalId())
        studyListService.deleteUserStudyList(userInternalId, songId)
        return ResponseEntity.ok(
                responseService.getSuccessMessageResult(
                        "스터디리스트 삭제 완료"
                )
        )
    }

    @GetMapping("/history")
    fun getUserHistory(
        @RequestParam(value = "time", required = true) time: String
    ): ResponseEntity<SingleResult<HistoryResponse>> {

        logger.info { "/history/$time" }
        val userInternalId = UUID.fromString(jwtService.getUserInternalId())

        time.toLongOrNull()?.let {
            return ResponseEntity.ok(
                    responseService.getSuccessSingleResult(
                            learnReportService.getUserLearnReport(userInternalId, it)
                            , "히스토리 조회 성공"
                    )
            )
        } ?: throw RequestNotNumberException("시간은 Long Type이어야 합니다")
    }

    @GetMapping("/history/fill/{learnReportId}")
    fun getUserHistoryFill(
            @PathVariable(value = "learnReportId", required = true) learnReportId: String
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
        @PathVariable(value = "learnReportId", required = true) learnReportId: String
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
        @PathVariable(value = "learnReportId", required = true) learnReportId: String
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
        @PathVariable(value = "learnReportId", required = true) learnReportId: String
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

    @PostMapping("/fill/submit")
    fun submitFill(
        @RequestBody request: SubmitFillRequest
    ): ResponseEntity<SingleResult<SubmitFillResponse>> {

        logger.debug { "/submit/fill $request" }
        val userInternalId = UUID.fromString(jwtService.getUserInternalId())

        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                        userService.markingFill(
                                userInternalId = userInternalId,
                                request = request
                        )
                        ,"Fill 제출 완료"
                )
        )
    }

    @PostMapping("/pick/submit")
    fun submitPick(
        @RequestBody request: SubmitPickRequest
    ): ResponseEntity<SingleResult<SubmitPickResponse>> {

        logger.debug { "/pick/submit $request" }
        val userInternalId = UUID.fromString(jwtService.getUserInternalId())

        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                        userService.markingPick(
                                userInternalId = userInternalId,
                                request = request
                        )
                        ,"Pick 제출 완료"
                )
        )
    }

    @PostMapping("/express/submit")
    fun submitExpress(
        @RequestBody request: SubmitExpressRequest
    ): ResponseEntity<CommonResult> {

        logger.debug { "/express/submit $request" }
        val userInternalId = UUID.fromString(jwtService.getUserInternalId())

        return ResponseEntity.ok(
                responseService.getSuccessMessageResult(
                        userService.saveExpress(
                                userInternalId = userInternalId,
                                request = request
                        )
                )
        )
    }

    @PostMapping("/pronounce/submit")
    fun submitPronounce(
            @RequestPart("userFile") userFile: MultipartFile
            , @RequestPart("ttsFile") ttsFile: MultipartFile
            , @RequestPart("json") request: SubmitPronounceRequest
    ): ResponseEntity<SingleResult<SubmitPronounceResponse>> {

        logger.debug { "/fill/pronounce $request" }
        val userInternalId = UUID.fromString(jwtService.getUserInternalId())

        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                        userService.analyzeAndSavePronounce(
                                userFile = userFile,
                                ttsFile = ttsFile,
                                request = request,
                                userInternalId = userInternalId
                        )
                        ,"Pronounce 제출 완료"
                )
        )
    }

    @GetMapping("/recommend")
    fun recommendMusic()
    : ResponseEntity<SingleResult<RecommendResponse>>{

        val userInternalId = UUID.fromString(jwtService.getUserInternalId())

        return ResponseEntity.ok(
            responseService.getSuccessSingleResult(
                recommendService.getRecommendations(userInternalId)
                ,"음악 추천 성공"
            )
        )
    }
}