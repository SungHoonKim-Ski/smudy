package com.ssafy.studyservice.db.postgre.repository

import com.ssafy.studyservice.db.postgre.entity.ProblemBox
import com.ssafy.studyservice.db.postgre.repository.mapping.SongIdMapping
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository


@Repository
interface ProblemBoxRepository : JpaRepository<ProblemBox, Int> {
    @Query("SELECT DISTINCT p.songId from ProblemBox p" +
            "")
    fun findAllDistinctBySongId(): List<String>
}