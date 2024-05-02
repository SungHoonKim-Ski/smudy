package com.ssafy.userservice.db.postgre.repository

import com.ssafy.userservice.db.postgre.entity.LearnReportExpress
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LearnReportExpressRepository : JpaRepository<LearnReportExpress, Int> {

}