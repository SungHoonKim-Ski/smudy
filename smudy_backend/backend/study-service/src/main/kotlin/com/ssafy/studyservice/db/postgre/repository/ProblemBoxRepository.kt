package com.ssafy.studyservice.db.postgre.repository

import com.ssafy.studyservice.db.postgre.entity.ProblemBox
import com.ssafy.studyservice.db.postgre.repository.mapping.SongIdMapping
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface ProblemBoxRepository : JpaRepository<ProblemBox, Int> {
    @Query("" +
            "SELECT " +
                "* " +
            "FROM " +
                "problem_box pb " +
            "WHERE " +
                "pb.song_id = :songId " +
            "ORDER BY " +
            "random() " +
            "limit 1"
            , nativeQuery = true)
    fun findRandomProblemBoxBySongId(@Param("songId")songId: String) : ProblemBox
}