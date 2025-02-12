package com.ssafy.userservice.service

import com.ssafy.userservice.config.ObjectMapperConfig
import com.ssafy.userservice.db.postgre.entity.*
import com.ssafy.userservice.db.postgre.repository.UserRepository
import com.ssafy.userservice.dto.request.*
import com.ssafy.userservice.dto.response.*
import com.ssafy.userservice.dto.response.ai.LyricAiAnalyze
import com.ssafy.userservice.exception.exception.LearnReportNotSavedException
import com.ssafy.userservice.exception.exception.UserNotFoundException
import com.ssafy.userservice.service.feign.StudyServiceClient
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class UserService(
        private val userRepository: UserRepository,
        private val songService: SongService,
        private val learnReportService: LearnReportService,
        private val studyServiceClient: StudyServiceClient,
        private val wrongService: WrongService,
        private val aiService: AiService,
) {

    private val logger = KotlinLogging.logger{ }

    @Transactional
    fun getUserInfo(userInternalId: UUID) : InfoResponse {

        val user = userRepository.findByUserInternalId(userInternalId)
        user ?: throw UserNotFoundException("존재하지 않는 유저")

        logger.info { "user : $user" }
        logger.info { "UUID : $userInternalId" }

        val userLeanReportSongIds = learnReportService.getUserTop4History(user.userInternalId)

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

        val song = songService.findSongBySongId(request.songId)
        val answers = song.songLyrics
        val userAnswer = request.userWords.map { it.word }
        val wrongs = mutableListOf<Wrong>()

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
                        val isCorrect = userAnswer[index] == lyric.lyricBlankAnswer
                        if (lyric.lyricBlankAnswer.isNotBlank()) totalSize++
                        if (isCorrect) score++
                        else {
                            wrongs.add(
                                    Wrong(
                                            userInternalId = userInternalId,
                                            lyricSentenceEn = lyric.lyricSentenceEn,
                                            lyricSentenceKo = lyric.lyricSentenceEn
                                    )
                            )
                        }
                        isCorrect
                    }
                )
            )
        }

        wrongService.saveAll(wrongs)


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

        val isCorrect = addStreak(
                userInternalId = userInternalId,
                total = totalSize,
                score = score,
                albumJacket = song.albumJacket
        )

        if (isCorrect) {
            userRepository.findByUserInternalId(userInternalId)?.let {
                it.userExpUp()
            } ?: throw UserNotFoundException("Fill 채점 도중 유저를 찾지 못함")
        }

        return SubmitFillResponse(
                totalSize = totalSize,
                score = score,
                result = result
        )
    }

    @Transactional
    fun markingPick(userInternalId: UUID, request: SubmitPickRequest) : SubmitPickResponse {

        val problems = studyServiceClient.getProblemsByProblemBoxId(request.problemBoxId)
        val song = songService.findSongBySongId(request.songId)
        val response = SubmitPickResponse()

        val wrongs = mutableListOf<Wrong>()

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
                response.wrong.add(
                        WrongProblem(
                            userLyricSentence = request.userPicks[index].userPick,
                            lyricSentenceEn = problem.sentenceEn,
                            lyricSentenceKo = problem.sentenceKo,
                        )
                )
            }
        }

        response.wrong.map {
            wrongs.add(
                    Wrong(
                            userInternalId = userInternalId,
                            lyricSentenceEn = it.lyricSentenceEn,
                            lyricSentenceKo = it.lyricSentenceKo,
                    )
            )
        }

        wrongService.saveAll(wrongs)


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

        val isCorrect = addStreak(
                total = userLearnReport.learnReportTotal,
                score = userLearnReport.learnReportTotal,
                userInternalId = userInternalId,
                albumJacket = song.albumJacket
        )

        if (isCorrect) {
            userRepository.findByUserInternalId(userInternalId)?.let {
                it.userExpUp()
            } ?: throw UserNotFoundException("Fill 채점 도중 유저를 찾지 못함")
        }

        return response
    }

    @Transactional
    fun saveExpress(userInternalId: UUID, request: SubmitExpressRequest) : String {

        val userExpresses = request.userExpresses
        val song = songService.findSongBySongId(request.songId)
        val wrongs = mutableListOf<Wrong>()

        val userLearnReport = learnReportService.saveLearnReport(
                LearnReport(
                        learnReportId = -1,
                        userInternalId = userInternalId,
                        songId = request.songId,
                        problemType = "EXPRESS",
                        learnReportScore = userExpresses.map { it.score }.average().toInt(),
                        learnReportTotal = 100,
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

        userExpresses.forEachIndexed { index, userExpress ->
            if (userExpress.score < 70) {
                wrongs.add(
                        Wrong(
                                userInternalId = userInternalId,
                                lyricSentenceKo = userExpress.suggestLyricSentence,
                                lyricSentenceEn = userExpress.suggestLyricSentence,
                        )
                )
            }
        }

        wrongService.saveAll(wrongs)

        val isCorrect = addStreak(
                average = userLearnReport.learnReportScore,
                userInternalId = userInternalId,
                albumJacket = song.albumJacket
        )

        if (isCorrect) {
            userRepository.findByUserInternalId(userInternalId)?.let {
                it.userExpUp()
            } ?: throw UserNotFoundException("Fill 채점 도중 유저를 찾지 못함")
        }

        return "Express 제출 완료"
    }

    @Transactional
    fun analyzeAndSavePronounce(
            userInternalId: UUID,
            request: SubmitPronounceRequest,
            userFile: MultipartFile,
            ttsFile: MultipartFile,
    ) : SubmitPronounceResponse {

        val analyzeResponse = getPronounceAnalyze(ttsFile = ttsFile, userFile = userFile)

        val mapper = ObjectMapperConfig().getObjectMapper()

        val learnReportPronounce = LearnReportPronounce(
                learnReportId = -1,
                learnReportPronounceUserEn = analyzeResponse.userFullText,
                lyricSentenceEn = request.lyricSentenceEn,
                lyricSentenceKo =  request.lyricSentenceKo,
                lyricAiAnalyze = mapper.writeValueAsString(analyzeResponse)
        )

        savePronounce(
                learnReportPronounce = learnReportPronounce,
                songId = request.songId,
                userInternalId = userInternalId
        )

        val song = songService.findSongBySongId(request.songId)

        addStreak(
                average = 100,
                userInternalId = userInternalId,
                albumJacket = song.albumJacket
        )

        userRepository.findByUserInternalId(userInternalId)?.let {
            it.userExpUp()
        } ?: throw UserNotFoundException("Pronounce 채점 도중 유저를 찾지 못함")


        return SubmitPronounceResponse(
                lyricSentenceEn = request.lyricSentenceEn,
                lyricSentenceKo = request.lyricSentenceKo,
                userLyricSttEn = analyzeResponse.userFullText,
                lyricAiAnalyze = analyzeResponse
        )
    }

    fun getPronounceAnalyze (
            userFile: MultipartFile,
            ttsFile: MultipartFile,
    ) : LyricAiAnalyze{
        return aiService.getPronounce(
                ttsFile = ttsFile, userFile = userFile
        )
    }

    @Transactional
    fun savePronounce(
            learnReportPronounce: LearnReportPronounce,
            songId: String,
            userInternalId: UUID,
    ) {

        val learnReport = learnReportService.saveLearnReport(
                                    LearnReport(
                                            learnReportId = -1,
                                            userInternalId = userInternalId,
                                            songId = songId,
                                            problemType = "PRONOUNCE",
                                            learnReportScore = -1,
                                            learnReportTotal = -1
                                    )
        )

        if (learnReport.learnReportId == -1) {
            throw LearnReportNotSavedException("PRONOUNCE 저장 중 에러")
        }

        learnReportPronounce.learnReportId = learnReport.learnReportId
        learnReportService.saveLearnReportPronounce(learnReportPronounce)
    }

    fun addStreak(total: Int, score: Int, userInternalId: UUID, albumJacket: String) : Boolean {
//        if (isCorrect(total, score)) {
            learnReportService.insertOrUpdateUserStreak(
                    Streak(
                            userInternalId = userInternalId,
                            songJacket = albumJacket,
                            streakDate = java.sql.Date(System.currentTimeMillis())
                    )
            )
            return true
//        }
//        return false
    }

    fun addStreak(average: Int, userInternalId: UUID, albumJacket: String) : Boolean {
//        if (isCorrect(average)) {
            learnReportService.insertOrUpdateUserStreak(
                    Streak(
                            userInternalId = userInternalId,
                            songJacket = albumJacket,
                            streakDate = java.sql.Date(System.currentTimeMillis())
                    )
            )
            return true
//        }
//        return false
    }

    fun isCorrect(total: Int, score: Int): Boolean {
        return score/total.toFloat() * 100 >= 70
    }

    fun isCorrect(average: Int) :  Boolean{
        return average >= 70
    }
}