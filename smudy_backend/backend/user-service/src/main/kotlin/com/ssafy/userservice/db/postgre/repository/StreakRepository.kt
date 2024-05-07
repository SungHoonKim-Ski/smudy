package com.ssafy.userservice.db.postgre.repository

import com.ssafy.userservice.db.postgre.entity.Streak
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StreakRepository : JpaRepository<Streak, Int> {
}