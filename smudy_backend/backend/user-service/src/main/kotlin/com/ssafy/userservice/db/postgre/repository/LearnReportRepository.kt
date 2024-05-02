package com.ssafy.userservice.db.postgre.repository

import com.ssafy.userservice.db.postgre.entity.LearnReport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LearnReportRepository : JpaRepository<LearnReport, Int> {

}