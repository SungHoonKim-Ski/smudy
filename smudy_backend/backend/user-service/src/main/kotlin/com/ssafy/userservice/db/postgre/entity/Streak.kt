package com.ssafy.userservice.db.postgre.entity

import jakarta.persistence.*
import java.sql.Date
import java.util.*

@Entity
@Table(name = "streak", schema = "public")
class Streak(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "strict_id")
        var streakId: Int = -1,

//        @ManyToOne
        @JoinColumn(name = "user_internal_id")
        var userInternalId: UUID,

        @Column(name = "song_jacket", nullable = false)
        var songJacket: String,

        @Column(name = "streak_date", nullable = false)
        @Temporal(TemporalType.DATE)
        var streakDate: Date
)