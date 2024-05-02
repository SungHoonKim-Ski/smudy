package com.ssafy.userservice.service

import com.ssafy.userservice.db.mongodb.repository.SongRepository
import com.ssafy.userservice.db.postgre.entity.LearnReport
import com.ssafy.userservice.db.postgre.repository.LearnReportRepository
import com.ssafy.userservice.db.postgre.repository.StreakRepository
import com.ssafy.userservice.dto.response.HistoryResponse
import com.ssafy.userservice.dto.response.LearnReportResponse
import com.ssafy.userservice.dto.response.StreakResponse
import com.ssafy.userservice.dto.response.StreakSimple
import com.ssafy.userservice.exception.exception.HistoryNotFoundException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Date
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class LearnReportService (
        private val learnReportRepository: LearnReportRepository,
        private val songRepository: SongRepository,
        private val streakRepository: StreakRepository,

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


}