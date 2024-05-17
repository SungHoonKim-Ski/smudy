package com.ssafy.userservice.service

import com.ssafy.userservice.config.ObjectMapperConfig
import com.ssafy.userservice.db.postgre.entity.*
import com.ssafy.userservice.db.postgre.repository.*
import com.ssafy.userservice.dto.response.*
import com.ssafy.userservice.dto.response.ai.LyricAiAnalyze
import com.ssafy.userservice.exception.exception.HistoryNotFoundException
import com.ssafy.userservice.exception.exception.LearnReportNotFoundException
import com.ssafy.userservice.service.feign.StudyServiceClient
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Date
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class LearnReportService (
        private val learnReportRepository: LearnReportRepository,
        private val streakRepository: StreakRepository,
        private val studyServiceClient: StudyServiceClient,
        private val songService: SongService,

        private val fillRepository: LearnReportFillRepository,
        private val pronounceRepository: LearnReportPronounceRepository,
        private val pickRepository: LearnReportPickRepository,
        private val expressRepository: LearnReportExpressRepository,

        @Value("\${image.streak}")
        val streakImageUrl: String
) {

    val logger = KotlinLogging.logger {  }
    val mapper = ObjectMapperConfig().getObjectMapper()


    @Transactional
    fun getUserStreak(userInternalId: UUID) : StreakResponse {

        val zoneId = ZoneId.of("Asia/Seoul")

        val allDates = (0..90).map { LocalDate.now().minusDays(it.toLong()) }
        val ninetyDaysAgo = LocalDate.now().minusDays(90).atStartOfDay(zoneId).toInstant().toEpochMilli()

        val userStreaks = streakRepository
                .findByUserInternalIdAndStreakDateGreaterThanEqual(
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
                    albumJacket = streakImageUrl,
                    streakDate = Date.valueOf(date.toString()).time
            )
        }

        return StreakResponse(completeStreakResponses)
    }

    @Transactional
    fun getUserLearnReport(userInternalId: UUID) : HistoryResponse {

        val userLearnReports = learnReportRepository.findAllByUserInternalId(
            userInternalId = userInternalId
        )

        if (userLearnReports.isEmpty())
//            throw HistoryNotFoundException("해당 날짜에 히스토리가 존재하지 않음")
            return HistoryResponse(emptyList())

        val songIdSongMap = songService.findAllBySongIdsIn(
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

    @Transactional
    fun getUserLearnReport(userInternalId: UUID, timestamp: Long) : HistoryResponse {

        val currentLocalDate = Date(timestamp).toLocalDate()
        
        val formatterStart = DateTimeFormatter.ofPattern("yyyy-MM-01")
        val startLocalDate = LocalDate.parse(currentLocalDate.format(formatterStart))

        val endDate = currentLocalDate.lengthOfMonth()
        val formatterEnd = DateTimeFormatter.ofPattern("yyyy-MM-$endDate")
        val endLocalDate = LocalDate.parse(currentLocalDate.format(formatterEnd))
        val oneDay = 86400000

        logger.info { "start : $startLocalDate" }

        logger.info { "end : $endLocalDate" }
        logger.info { "ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ" }
        
        logger.info { "start : ${Date(startLocalDate.toEpochDay() * oneDay)}" }

        logger.info { "end : ${Date(endLocalDate.toEpochDay() * oneDay)}" }

        val userLearnReports = learnReportRepository.findAllByUserInternalIdAndLearnReportDateBetween(
                        userInternalId = userInternalId
                        , startDate = Date(startLocalDate.toEpochDay() * oneDay)
                        , endDate = Date(endLocalDate.toEpochDay()  * oneDay)
                )

        if (userLearnReports.isEmpty()) throw HistoryNotFoundException("해당 날짜에 히스토리가 존재하지 않음")

        val songIdSongMap = songService.findAllBySongIdsIn(
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

        val song = songService.findSongBySongId(history.songId)

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

        val problems = studyServiceClient.getProblemsByProblemBoxId(details.problemBoxId)

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

    fun getUserExpressHistory(userLeanHistoryId: Int) : HistoryExpressResponse {

        val history = getUserHistory(userLeanHistoryId)
        val details = expressRepository.findById(history.learnReportId).getOrNull()
                ?: throw LearnReportNotFoundException("Express에 해당하는 학습 기록 상세 ID가 존재하지 않음")
        logger.info { "box : ${details.problemBoxId}" }

        val problems = studyServiceClient.getProblemsByProblemBoxId(details.problemBoxId)

        val expresses = problems.mapIndexed { index, problem ->
            UserExpressHistory(
                    userLyricSentenceKo = details.learnReportExpressUserKo[index],
                    userLyricSentenceEn = details.learnReportExpressUserEn[index],
                    suggestLyricSentence = details.learnReportExpressSuggest[index],
                    score = details.learnReportExpressScore[index],
                    lyricSentenceKo = problem.sentenceKo,
                    lyricSentenceEn = problem.sentenceEn,
            )
        }

        return HistoryExpressResponse(expresses)
    }

    fun getUserPronounceHistory(userLeanHistoryId: Int): HistoryPronounceResponse {

        val details = pronounceRepository.findById(userLeanHistoryId).getOrNull()
                ?: throw LearnReportNotFoundException("Express에 해당하는 학습 기록 상세 ID가 존재하지 않음")
        logger.info { "box : ${details.learnReportId}" }

        return HistoryPronounceResponse(
                userLyricEn = details.learnReportPronounceUserEn,
                lyricSentenceEn = details.lyricSentenceEn,
                lyricSentenceKo = details.lyricSentenceKo,
                lyricAiAnalyze = mapper.readValue(details.lyricAiAnalyze, LyricAiAnalyze::class.java),
        )
    }

    @Transactional
    fun insertOrUpdateUserStreak(streak: Streak) : Boolean {
        val existStreak = streakRepository.findByUserInternalIdAndStreakDate(streak.userInternalId, streak.streakDate)

        existStreak?.let {
            existStreak.songJacket = streak.songJacket
            return false
        } ?: streakRepository.save(streak)

        return true
    }

    fun getUserTop4History(userInternalId: UUID) : List<LearnReport> {
        return learnReportRepository.findTop4ByUserInternalId(userInternalId)
    }


    fun saveLearnReport(userLearnReport: LearnReport) : LearnReport {
        return learnReportRepository.save(userLearnReport)
    }

    fun saveLearnReportFill(userFill: LearnReportFill) {
        fillRepository.save(userFill)
    }

    fun saveLearnReportPick(userPick: LearnReportPick) {
        pickRepository.save(userPick)
    }

    fun saveLearnReportPronounce(userPronounce: LearnReportPronounce) {
        pronounceRepository.save(userPronounce)
    }

    fun saveLearnReportExpress(userExpress: LearnReportExpress) {
        expressRepository.save(userExpress)
    }
}