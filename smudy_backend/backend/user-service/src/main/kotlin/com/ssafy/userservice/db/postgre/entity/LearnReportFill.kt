package com.ssafy.userservice.db.postgre.entity

import io.hypersistence.utils.hibernate.type.array.BooleanArrayType
import io.hypersistence.utils.hibernate.type.array.StringArrayType
import jakarta.persistence.*
import org.hibernate.annotations.Type

@Entity
@Table(name = "learn_report_fill", schema = "public")

class LearnReportFill(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "learn_report_id")
        var learnReportId: Int,

        @OneToOne
        @MapsId
        @JoinColumn(name = "learn_report_id")
        var learnReport: LearnReport,

        @Column(name = "song_id", length = 30, nullable = false)
        var songId: String,

//        @Convert(converter = StringArrayType::class)
//        @Type(value = StringArrayType::class)
        @Column(name = "learn_report_fill_user", columnDefinition = "text[]", nullable = false)
        var learnReportFillUser: Array<String>,


//        @Type(value = BooleanArrayType::class)
        @Column(name = "learn_report_fill_is_correct", columnDefinition = "bool[]", nullable = false)
        var learnReportFillIsCorrect: List<Boolean>,

)