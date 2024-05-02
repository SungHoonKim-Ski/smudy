package com.ssafy.userservice.db.postgre.repository

import com.ssafy.userservice.db.postgre.entity.LearnReport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.sql.Date
import java.util.*

@Repository
interface LearnReportRepository : JpaRepository<LearnReport, Int> {
    fun findTop4ByUserInternalId(userInternalId: UUID): List<LearnReport>

//    @Query("" +
//            "SELECT " +
//                "e " +
//            "FROM " +
//                "LearnReport e " +
//            "WHERE " +
//                "e.learnReportDate >= :date " +
//            "ORDER BY " +
//                "e.learnReportDate ASC" +
//            "")
    fun findFirstByUserInternalIdAndLearnReportDateAfter(userInternalId: UUID
                                                         , learnReportDate: Date): List<LearnReport>
}