package com.ssafy.studyservice.db.postgre.entity

import jakarta.persistence.*

@Entity
@Table(name = "problem_box")
class ProblemBox(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "problem_box_id", nullable = false)
        val problemBoxId: Int,

        @Column(name = "song_id", nullable = false, length = 30)
        val songId: String
)
