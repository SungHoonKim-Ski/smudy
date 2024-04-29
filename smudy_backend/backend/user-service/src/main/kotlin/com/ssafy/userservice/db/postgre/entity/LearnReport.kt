package com.ssafy.userservice.db.postgre.entity

import java.sql.Date

class LearnReport {
    var learnReportId: Long = 0
    var userId: Long = 0
    var songId: String? = null
    var problemType: String? = null
    var learnReportDate: Date? = null
    var learnReportScore: Long = 0
    var learnReportTotal: Long = 0
}