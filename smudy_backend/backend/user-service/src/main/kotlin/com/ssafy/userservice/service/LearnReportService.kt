package com.ssafy.userservice.service

import com.ssafy.userservice.db.mongodb.repository.SongRepository
import com.ssafy.userservice.db.postgre.entity.LearnReport
import com.ssafy.userservice.db.postgre.repository.LearnReportRepository
import com.ssafy.userservice.db.postgre.repository.StreakRepository
import com.ssafy.userservice.dto.response.StreakResponse
import com.ssafy.userservice.dto.response.StreakSimple
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Date
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@Service
class LearnReportService (
        private val learnReportRepository: LearnReportRepository,
        private val songRepository: SongRepository,
        private val streakRepository: StreakRepository,

) {
    val logger = KotlinLogging.logger {  }
    val ninetyDaysAgo = LocalDate.now().minusDays(90).toEpochDay()
//    val ninetyDaysAgo = java.sql.Date(System.currentTimeMillis() - 90 * 24 * 60 * 60 * 1000L)
    @Transactional
    fun getUserStreak(userInternalId: UUID) : StreakResponse {
//        val userStreaks = getRecentUserStreaks(userInternalId);
//        return StreakResponse(fillEmptyStreaks(userStreaks))

        val allDates = (0..90).map { LocalDate.now().minusDays(it.toLong()) }


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
//                    albumJacket = "https://smudy.s3.ap-northeast-2.amazonaws.com/songs/albumart.jpg",
                    streakDate = Date.valueOf(date.toString()).time
            )
        }

        return StreakResponse(completeStreakResponses)
    }

}