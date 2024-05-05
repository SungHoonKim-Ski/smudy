package com.ssafy.userservice.service

import com.ssafy.userservice.db.postgre.entity.*
import com.ssafy.userservice.db.postgre.repository.UserRepository
import com.ssafy.userservice.dto.request.SignUpRequest
import com.ssafy.userservice.dto.request.SubmitExpressRequest
import com.ssafy.userservice.dto.request.SubmitFillRequest
import com.ssafy.userservice.dto.request.SubmitPickRequest
import com.ssafy.userservice.dto.response.*
import com.ssafy.userservice.dto.response.feign.ExpressResponse
import com.ssafy.userservice.exception.exception.LearnReportNotSavedException
import com.ssafy.userservice.exception.exception.UserNotFoundException
import com.ssafy.userservice.service.feign.StudyServiceClient
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService(
        private val userRepository: UserRepository,
        private val songService: SongService,
        private val learnReportService: LearnReportService,
        private val studyServiceClient: StudyServiceClient,

) {

    private val logger = KotlinLogging.logger{ }
    @Transactional
    fun signup(signUpRequest: SignUpRequest) : String{

        userRepository.findByUserSnsId(signUpRequest.userSnsId) ?: return ""

        val saveUser = userRepository
                .save(
                    User(
                            userId = -1,
                            userExp = 0,
                            userName = signUpRequest.userSnsName,
                            userImage = signUpRequest.userImage,
                            userInternalId = UUID.randomUUID(),
                            userSnsId = signUpRequest.userSnsId,
                            createdAt = Date()
                    )
                )

        saveUser.userId

        if (saveUser.userId == -1) return ""

        return saveUser.userInternalId.toString()
    }

    @Transactional
    fun getUserInfo(userInternalId: UUID) : InfoResponse {

        val user = userRepository.findByUserInternalId(userInternalId)
        user ?: throw UserNotFoundException("존재하지 않는 유저")

        logger.info { "user : $user" }
        logger.info { "UUID : $userInternalId" }

        val userLeanReportSongIds = learnReportService
                .getUserTop4History(user.userInternalId)
                .map { learnReport -> learnReport.songId }

        logger.info { "userLeanReportSongIds : $userLeanReportSongIds" }

        val userLearnReportSongs = songService.findAllBySongIdsIn(userLeanReportSongIds)

        return InfoResponse(
                userName = user.userName,
                userImage = user.userImage,
                userExp = user.userExp,
                userStudyHistory = userLearnReportSongs
        )

    }

    @Transactional
    fun markingFill(userInternalId: UUID, request: SubmitFillRequest) : SubmitFillResponse{

        val answers = songService.findSongBySongId(request.songId).songLyrics
        val userAnswer = request.userWords.map { it.word }

        var score = 0
        var totalSize = 0
        val result = mutableListOf<FillResult>()

        answers.forEachIndexed { index, lyric ->
            result.add(
                    FillResult(
                    lyricBlank = lyric.lyricBlank,
                    originWord = lyric.lyricBlankAnswer,
                    userWord = userAnswer[index],
                    isCorrect = lyric.lyricBlankAnswer.let {
                        totalSize++

                        val isCorrect = userAnswer[index] == lyric.lyricBlankAnswer
                        if (isCorrect) score++

                        isCorrect
                    }
                )
            )
        }

        val userLearnReport = learnReportService.saveLearnReport(
                LearnReport(
                        learnReportId = -1,
                        userInternalId = userInternalId,
                        songId = request.songId,
                        problemType = "FILL",
                        learnReportScore = score,
                        learnReportTotal = totalSize,
                )
        )

        if (userLearnReport.learnReportId == -1) {
            throw LearnReportNotSavedException("Fill 채점 에러")
        }

        logger.info { "userLearnReport : $userLearnReport" }

        val learnReportDetail = learnReportService.saveLearnReportFill(
                LearnReportFill(
                        learnReportId = userLearnReport.learnReportId,
                        songId = request.songId,
                        learnReportFillUser = result.map { it.userWord },
                        learnReportFillIsCorrect = result.map { it.isCorrect }
                )
        )

        return SubmitFillResponse(
                totalSize = totalSize,
                score = score,
                result = result
        )
    }

    @Transactional
    fun markingPick(userInternalId: UUID, request: SubmitPickRequest) : SubmitPickResponse {

        val problems = studyServiceClient.getProblemsByProblemBoxId(request.problemBoxId)

        val response = SubmitPickResponse()

        problems.forEachIndexed { index, problem ->
            if (problem.sentenceEn == request.userPicks[index].userPick) {
                response.correct.add(
                        ProblemResponse(
                                lyricSentenceEn = problem.sentenceEn,
                                lyricSentenceKo = problem.sentenceKo
                        )
                )
                response.score++
            } else {
                response.wrong.add(WrongProblem(
                        userLyricSentence = request.userPicks[index].userPick,
                        lyricSentenceEn = problem.sentenceEn,
                        lyricSentenceKo = problem.sentenceKo,
                ))
            }
        }

        val userLearnReport = learnReportService.saveLearnReport(
                LearnReport(
                        learnReportId = -1,
                        userInternalId = userInternalId,
                        songId = request.songId,
                        problemType = "PICK",
                        learnReportScore = response.score,
                        learnReportTotal = response.totalSize,
                )
        )

        if (userLearnReport.learnReportId == -1) {
            throw LearnReportNotSavedException("PICK 채점 에러")
        }

        learnReportService.saveLearnReportPick(
                LearnReportPick(
                    learnReportId = userLearnReport.learnReportId,
                    problemBoxId = request.problemBoxId,
                    learnReportPickUser = request.userPicks.map { it.userPick },
                )
        )
        return response
    }

    @Transactional
    fun saveExpress(userInternalId: UUID, request: SubmitExpressRequest) : String {

        val userExpresses = request.userExpresses

        val userLearnReport = learnReportService.saveLearnReport(
                LearnReport(
                        learnReportId = -1,
                        userInternalId = userInternalId,
                        songId = request.songId,
                        problemType = "EXPRESS",
                        learnReportScore = userExpresses.map { it.score }.average().toInt(),
                        learnReportTotal = 5,
                )
        )

        if (userLearnReport.learnReportId == -1) {
            throw LearnReportNotSavedException("EXPRESS 제출 에러")
        }

        learnReportService.saveLearnReportExpress(
                LearnReportExpress(
                        learnReportId = userLearnReport.learnReportId,
                        problemBoxId = request.problemBoxId,
                        learnReportExpressScore = userExpresses.map { it.score },
                        learnReportExpressSuggest = userExpresses.map { it.suggestLyricSentence },
                        learnReportExpressUserEn = userExpresses.map { it.userLyricSentenceEn },
                        learnReportExpressUserKo = userExpresses.map { it.userLyricSentenceKo }
                )
        )

        return "Express 제출 완료"
    }
}