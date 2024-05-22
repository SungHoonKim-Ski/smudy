package com.ssafy.studyservice.db.postgre.repository

import com.ssafy.studyservice.db.postgre.entity.DailyLyrics
import com.ssafy.studyservice.db.postgre.entity.Problem
import com.ssafy.studyservice.db.postgre.entity.ProblemBox
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProblemRepository : JpaRepository<Problem, Int> {
    fun findByProblemBoxId(problemBoxId: Int): List<Problem>

    @Query("" +
            "SELECT " +
            "* " +
            "FROM " +
            "problem p " +
            "ORDER BY " +
            "random() " +
            "limit 1"
            , nativeQuery = true)
    fun getRandomLyric(): Problem
}