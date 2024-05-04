package com.ssafy.userservice.db.postgre.entity

import io.hypersistence.utils.hibernate.type.array.IntArrayType
import io.hypersistence.utils.hibernate.type.array.StringArrayType
import jakarta.persistence.*
import org.hibernate.annotations.Type

@Entity
@Table(name = "learn_report_express", schema = "public")
class LearnReportExpress(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "learn_report_id")
        var learnReportId: Int,

        @OneToOne
        @MapsId
        @JoinColumn(name = "learn_report_id")
        var learnReport: LearnReport,

        @Column(name = "problem_box_id", nullable = false)
        var problemBoxId: Int,

        @Column(name = "learn_report_express_suggest", columnDefinition = "TEXT[]", nullable = false)
        var learnReportExpressSuggest: List<String>,

//        @Convert(converter = StringArrayType::class)
//        @Type(value = StringArrayType::class)
        @Column(name = "learn_report_pick_user_en", columnDefinition = "TEXT[]", nullable = false)
        var learnReportPickUserEn: List<String>,

//        @Convert(converter = StringArrayType::class)
//        @Type(value = StringArrayType::class)
        @Column(name = "learn_report_pick_user_ko", columnDefinition = "TEXT[]", nullable = false)
        var learnReportPickUserKo: List<String>,

//        @Convert(converter = IntArrayType::class)
//        @Type(value = IntArrayType::class)
        @Column(name = "learn_report_express_score", columnDefinition = "INT[]", nullable = false)
        var learnReportExpressScore: List<Int>,
)