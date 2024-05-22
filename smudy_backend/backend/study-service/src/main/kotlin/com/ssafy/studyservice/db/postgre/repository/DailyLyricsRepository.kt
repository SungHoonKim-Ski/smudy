package com.ssafy.studyservice.db.postgre.repository

import com.ssafy.studyservice.db.postgre.entity.DailyLyrics
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DailyLyricsRepository : JpaRepository<DailyLyrics, Int> {

}