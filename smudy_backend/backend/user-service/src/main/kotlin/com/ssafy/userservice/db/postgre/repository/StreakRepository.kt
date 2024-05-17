package com.ssafy.userservice.db.postgre.repository

import com.ssafy.userservice.db.postgre.entity.Streak
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.sql.Date
import java.util.*

@Repository
interface StreakRepository : JpaRepository<Streak, Int> {

    fun findByUserInternalIdAndStreakDateGreaterThan(
            userInternalId: UUID, streakDate: Date
    ): List<Streak>

    fun findByUserInternalIdAndStreakDate(
            userInternalId: UUID, currentDate: Date
    ) : Streak?
}