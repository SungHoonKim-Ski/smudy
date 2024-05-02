package com.ssafy.userservice.db.postgre.entity

import jakarta.persistence.*
import java.sql.Date

@Entity
@Table(name = "learn_report", schema = "public")
class LearnReport(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "learn_report_id")
        var learnReportId: Int = 0,

        @ManyToOne
        @JoinColumn(name = "user_id")
        var user: User,

        @Column(name = "song_id", length = 30, nullable = false)
        var songId: String,

        @Column(name = "problem_type", nullable = false)
        var problemType: String,

        @Column(name = "learn_report_date", nullable = false)
        @Temporal(TemporalType.DATE)
        var learnReportDate: Date,

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
                        throw IllegalArgumentException("Invalid problem type.")
                }
        }
}