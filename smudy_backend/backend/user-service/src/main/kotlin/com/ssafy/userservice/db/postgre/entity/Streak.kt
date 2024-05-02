package com.ssafy.userservice.db.postgre.entity

import jakarta.persistence.*
import java.sql.Date

@Entity
@Table(name = "streak", schema = "public")
class Streak(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "strict_id")
        var streakId: Int,

        @ManyToOne
        @JoinColumn(name = "user_id")
        var user: User,

        @Column(name = "song_jacket", nullable = false)
        var songJacket: String,

        @Column(name = "streak_date", nullable = false)
        @Temporal(TemporalType.DATE)
        var streakDate: Date
)