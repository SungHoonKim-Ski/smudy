package com.ssafy.userservice.service

import com.fasterxml.jackson.core.JsonProcessingException
import com.ssafy.userservice.config.ObjectMapperConfig
import com.ssafy.userservice.db.feign.Problem
import com.ssafy.userservice.db.mongodb.repository.SongRepository
import com.ssafy.userservice.db.postgre.entity.LearnReport
import com.ssafy.userservice.db.postgre.repository.*
import com.ssafy.userservice.dto.response.*
import com.ssafy.userservice.dto.response.ai.LyricAiAnalyze
import com.ssafy.userservice.exception.exception.FeignException
import com.ssafy.userservice.exception.exception.HistoryNotFoundException
import com.ssafy.userservice.exception.exception.LearnReportNotFoundException
import com.ssafy.userservice.service.feign.StudyServiceClient
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Date
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class LearnReportService (
        private val learnReportRepository: LearnReportRepository,
        private val songRepository: SongRepository,
        private val streakRepository: StreakRepository,
        private val fillRepository: LearnReportFillRepository,
        private val pronounceRepository: LearnReportPronounceRepository,
        private val pickRepository: LearnReportPickRepository,
        private val expressRepository: LearnReportExpressRepository,
        private val studyServiceClient: StudyServiceClient,
        private val objectMapperConfig: ObjectMapperConfig,
) {
    val logger = KotlinLogging.logger {  }
    @Transactional
    fun getUserStreak(userInternalId: UUID) : StreakResponse {

        val allDates = (0..90).map { LocalDate.now().minusDays(it.toLong()) }
        val ninetyDaysAgo = LocalDate.now().minusDays(90).toEpochDay()

        val userStreaks = streakRepository
                .findALlByUserInternalIdAndStreakDateAfter(
                        userInternalId
                        , Date(ninetyDaysAgo)
                )

        val streaksMap = userStreaks.associateBy { it.streakDate.toLocalDate() }

        // 모든 날짜를 순회하며 데이터가 없는 경우 디폴트 값 추가
        val completeStreakResponses = allDates.map { date ->
            streaksMap[date]?.let { streak ->
                StreakSimple(
                        albumJacket = streak.songJacket,
                        streakDate = streak.streakDate.time
                )
            } ?: StreakSimple(
                    streakDate = Date.valueOf(date.toString()).time
            )
        }

        return StreakResponse(completeStreakResponses)
    }

    @Transactional
    fun getUserLearnReport(userInternalId: UUID, timestamp: Long) : HistoryResponse {

        val currentLocalDate = Date(timestamp * 1000).toLocalDate()
        val formatterStart = DateTimeFormatter.ofPattern("yyyy-MM-01")
        val startLocalDate = LocalDate.parse(currentLocalDate.format(formatterStart))

        val endDate = currentLocalDate.lengthOfMonth()
        val formatterEnd = DateTimeFormatter.ofPattern("yyyy-MM-$endDate")
        val endLocalDate = LocalDate.parse(currentLocalDate.format(formatterEnd))

        val oneDay = 86400000
        logger.info { "start : ${Date(startLocalDate.toEpochDay() * oneDay)}" }

        logger.info { "end : ${Date(endLocalDate.toEpochDay() * oneDay)}" }

        val userLearnReports = learnReportRepository.findAllByUserInternalIdAndLearnReportDateBetween(
                        userInternalId = userInternalId
                        , startDate = Date(startLocalDate.toEpochDay() * oneDay)
                        , endDate = Date(endLocalDate.toEpochDay()  * oneDay)
                )

        if (userLearnReports.isEmpty()) throw HistoryNotFoundException("해당 날짜에 히스토리가 존재하지 않음")

        val songIdSongMap = songRepository.findAllBySpotifyIdIn(
                userLearnReports.map { it.songId }
        ).associateBy { it.spotifyId }

        val response = userLearnReports.map { learnReport ->
            val song = songIdSongMap[learnReport.songId]!!
            LearnReportResponse(
                    learnReportId = learnReport.learnReportId
                    , albumJacket = song.albumJacket
                    , songName = song.songName
                    , songArtist = song.songArtist
                    , problemType = learnReport.problemType
                    , learnReportDate = learnReport.learnReportDate.time
            )
        }
        return HistoryResponse(response)
    }

    fun getUserHistory (userLeanHistoryId: Int) : LearnReport {
        return learnReportRepository.findById(userLeanHistoryId).getOrNull()
                ?: throw LearnReportNotFoundException("해당하는 학습 기록 ID가 존재하지 않음")
    }
    @Transactional
    fun getUserFillHistory(userLeanHistoryId: Int) : HistoryFillResponse {

        val history = getUserHistory(userLeanHistoryId)
        val detail = fillRepository.findByLearnReportId(history.learnReportId)
                ?: throw LearnReportNotFoundException("FILL에 해당하는 학습 기록 ID가 존재하지 않음")

        val song = songRepository.findBySpotifyId(history.songId)
                ?: throw LearnReportNotFoundException("학습 기록에 해당하는 노래가 존재하지 않음")

        val fillResult = song.songLyrics
                .zip(detail.learnReportFillUser) { lyric, userWord ->
                    Pair(lyric, userWord)
                }
                .zip(detail.learnReportFillIsCorrect) { pair, isCorrect ->
                    FillResult(
                            lyricBlank = pair.first.lyricBlank,
                            originWord = pair.first.lyricBlankAnswer,
                            userWord = pair.second,
                            isCorrect = isCorrect
                    )
                }
                .toList()

        return HistoryFillResponse(
                totalSize = history.learnReportTotal,
                score = history.learnReportScore,
                result = fillResult
        )
    }

    @Transactional
    fun getUserPickHistory(userLeanHistoryId: Int) : HistoryPickResponse {

        val history = getUserHistory(userLeanHistoryId)
        val details = pickRepository.findById(history.learnReportId).getOrNull()
                ?: throw LearnReportNotFoundException("PICK에 해당하는 학습 기록 상세 ID가 존재하지 않음")
        logger.info { "box : ${details.problemBoxId}" }

        lateinit var problems: List<Problem>

        try {
            problems = studyServiceClient.getProblemsByProblemBoxId(details.problemBoxId)
        } catch (e: JsonProcessingException) {
            throw FeignException("문제 리스트 조회 실패")
        }

        if (problems.isEmpty()) throw FeignException("문제 리스트 조회 실패")

        val correctList = mutableListOf<ProblemResponse>()
        val wrongList = mutableListOf<WrongProblem>()

        problems.forEachIndexed { index, problem ->
            val userPick = details.learnReportPickUser[index]
            if (problem.sentenceEn == userPick) {
                correctList.add(
                        ProblemResponse(
                                lyricSentenceKo = problem.sentenceKo,
                                lyricSentenceEn = problem.sentenceEn
                        )
                )
            } else {
                wrongList.add(
                        WrongProblem(
                                userLyricSentence = userPick,
                                lyricSentenceKo = problem.sentenceKo,
                                lyricSentenceEn = problem.sentenceEn
                        )
                )
            }
        }


        return HistoryPickResponse(
                score = history.learnReportScore,
                wrong = wrongList,
                correct = correctList
        )
    }

    @Transactional
    fun getUserExpressHistory(userLeanHistoryId: Int) : HistoryExpressResponse {

        val history = getUserHistory(userLeanHistoryId)
        val details = expressRepository.findById(history.learnReportId).getOrNull()
                ?: throw LearnReportNotFoundException("Express에 해당하는 학습 기록 상세 ID가 존재하지 않음")
        logger.info { "box : ${details.problemBoxId}" }

        lateinit var problems: List<Problem>

        try {
            problems = studyServiceClient.getProblemsByProblemBoxId(details.problemBoxId)
        } catch (e: JsonProcessingException) {
            throw FeignException("문제 리스트 조회 실패")
        }

        if (problems.isEmpty()) throw FeignException("문제 리스트 조회 실패")

        val expresses = problems.mapIndexed { index, problem ->
            UserExpress(
                    userLyricSentenceKo = details.learnReportPickUserKo[index],
                    userLyricSentenceEn = details.learnReportPickUserEn[index],
                    suggestLyricSentence = details.learnReportExpressSuggest[index],
                    score = details.learnReportExpressScore[index],
                    lyricSentenceEn = problem.sentenceEn,
                    lyricSentenceKo = problem.sentenceKo
            )
        }

        return HistoryExpressResponse(expresses)
    }

    @Transactional
    fun getUserPronounceHistory(userLeanHistoryId: Int): HistoryPronounceResponse {

        val details = pronounceRepository.findById(userLeanHistoryId).getOrNull()
                ?: throw LearnReportNotFoundException("Express에 해당하는 학습 기록 상세 ID가 존재하지 않음")
        logger.info { "box : ${details.learnReportId}" }
        return HistoryPronounceResponse(
                userLyricSttEn = details.learnReportPronounceSttEn,
                userLyricSttKo = details.learnReportPronounceSttKo,
                userPronounce = details.learnReportUserPronounce,
                ttsPronounce = details.learnReportTtsPronounce,
                lyricSentenceEn = details.lyricSentenceEn,
                lyricSentenceKo = details.lyricSentenceKo,
                lyricAiAnalyze = details.lyricAiAnalyze,
        )
    }
}