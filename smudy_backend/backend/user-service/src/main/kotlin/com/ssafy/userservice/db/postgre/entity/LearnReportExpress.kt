package com.ssafy.userservice.db.postgre.entity

import io.hypersistence.utils.hibernate.type.array.IntArrayType
import io.hypersistence.utils.hibernate.type.array.StringArrayType
import jakarta.persistence.*
import org.hibernate.annotations.Type

@Entity
@Table(name = "learn_report_express", schema = "public")
class LearnReportExpress(

        @Id
        @Column(name = "learn_report_id")
        var learnReportId: Int,

        @Column(name = "problem_box_id", nullable = false)
        var problemBoxId: Int,

        @Column(name = "learn_report_express_suggest", columnDefinition = "TEXT[]", nullable = false)
        var learnReportExpressSuggest: List<String>,

        @Column(name = "learn_report_express_user_en", columnDefinition = "TEXT[]", nullable = false)
        var learnReportExpressUserEn: List<String>,

        @Column(name = "learn_report_express_user_ko", columnDefinition = "TEXT[]", nullable = false)
        var learnReportExpressUserKo: List<String>,

        @Column(name = "learn_report_express_score", columnDefinition = "INT[]", nullable = false)
        var learnReportExpressScore: List<Int>,
)