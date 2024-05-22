package com.ssafy.userservice.db.postgre.entity

import io.hypersistence.utils.hibernate.type.array.StringArrayType
import jakarta.persistence.*
import org.hibernate.annotations.Type

@Entity
@Table(name = "learn_report_pick", schema = "public")
class LearnReportPick(

        @Id
        @Column(name = "learn_report_id")
        var learnReportId: Int,

        @Column(name = "problem_box_id", nullable = false)
        var problemBoxId: Int,

        @Column(name = "learn_report_pick_user", columnDefinition = "TEXT[]", nullable = false)
        var learnReportPickUser: List<String>,

)