package com.ssafy.userservice.db.postgre.repository

import com.ssafy.userservice.db.postgre.entity.LearnReportFill
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import javax.swing.text.html.Option

@Repository
interface LearnReportFillRepository : JpaRepository<LearnReportFill, Int> {
    fun findByLearnReportId(learnReportId: Int): LearnReportFill?
}