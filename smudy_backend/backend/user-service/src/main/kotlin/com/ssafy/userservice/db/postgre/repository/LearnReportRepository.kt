package com.ssafy.userservice.db.postgre.repository

import com.ssafy.userservice.db.postgre.entity.LearnReport
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.sql.Date
import java.util.*

@Repository
interface LearnReportRepository : JpaRepository<LearnReport, Int> {
    fun findTop4ByUserInternalId(userInternalId: UUID): List<LearnReport>

    fun findAllByUserInternalIdAndLearnReportDateBetween(
        userInternalId: UUID, startDate: Date, endDate: Date
    ): List<LearnReport>

    fun findAllByUserInternalId(
        userInternalId: UUID
    ): List<LearnReport>
}