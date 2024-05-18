package com.ssafy.userservice.db.postgre.repository

import com.ssafy.userservice.db.postgre.entity.LearnReport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.sql.Date
import java.util.*

@Repository
interface LearnReportRepository : JpaRepository<LearnReport, Int> {

    @Query("" +
            "SELECT " +
            "DISTINCT " +
                "lr.song_id " +
            "FROM " +
                "learn_report lr " +
            "WHERE " +
                "lr.user_internal_id = :userInternalId " +
            "FETCH FIRST 4 ROWS ONLY "
            , nativeQuery = true)
    fun findTop4ByUserInternalId(@Param("userInternalId") userInternalId: UUID): List<String>

    fun findAllByUserInternalIdAndLearnReportDateBetween(
        userInternalId: UUID, startDate: Date, endDate: Date
    ): List<LearnReport>

    fun findAllByUserInternalId(
        userInternalId: UUID
    ): List<LearnReport>
}