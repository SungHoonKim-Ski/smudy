package com.ssafy.userservice.db.postgre.repository

import com.ssafy.userservice.db.postgre.entity.LearnReportFill
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LearnReportFillRepository : JpaRepository<LearnReportFill, Int> {
}