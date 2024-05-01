package com.ssafy.userservice.db.postgre.entity

import io.hypersistence.utils.hibernate.type.array.StringArrayType
import jakarta.persistence.*
import org.hibernate.annotations.Type

@Entity
@Table(name = "learn_report_pick", schema = "public")
class LearnReportPick(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "learn_report_id")
        var learnReportId: Int = 0,

        @ManyToOne
        @MapsId
        @JoinColumn(name = "learn_report_id")
        var learnReport: LearnReport,

        @Column(name = "problem_box_id", nullable = false)
        var problemBoxId: Long = 0,

//        @Convert(converter = StringArrayType::class)
        @Type(value = StringArrayType::class)
        @Column(name = "learn_report_pick_user", columnDefinition = "TEXT[]", nullable = false)
        var learnReportPickUser: List<String> = listOf()
)