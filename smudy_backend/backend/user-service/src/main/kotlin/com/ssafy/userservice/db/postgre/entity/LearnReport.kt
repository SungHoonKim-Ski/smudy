package com.ssafy.userservice.db.postgre.entity

import com.ssafy.userservice.exception.exception.StudyIllegalTypeException
import jakarta.persistence.*
import java.sql.Date
import java.util.UUID

@Entity
@Table(name = "learn_report", schema = "public")
class LearnReport(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "learn_report_id")
        var learnReportId: Int = 0,

//        @ManyToOne
        @JoinColumn(name = "user_internal_id")
        var userInternalId: UUID,

        @Column(name = "song_id", length = 30, nullable = false)
        var songId: String,

        @Column(name = "problem_type", nullable = false)
        var problemType: String,

        @Column(name = "learn_report_date", nullable = false)
        @Temporal(TemporalType.DATE)
        var learnReportDate: Date = Date(System.currentTimeMillis()),

        @Column(name = "learn_report_score", nullable = false)
        var learnReportScore: Int,

        @Column(name = "learn_report_total", nullable = false)
        var learnReportTotal: Int
) {
        @PrePersist
        @PreUpdate
        private fun validateProblemType() {
                val validTypes = listOf("FILL", "PICK", "EXPRESS", "PRONOUNCE")
                if (problemType !in validTypes) {
                        throw StudyIllegalTypeException("Invalid problem type.")
                }
        }
}