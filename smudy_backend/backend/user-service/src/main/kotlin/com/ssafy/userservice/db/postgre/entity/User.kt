package com.ssafy.userservice.db.postgre.entity

import jakarta.persistence.*
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "users", schema = "public")
class User(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        var userId: Int,

        @Column(name = "user_internal_id", unique = true, nullable = true)
        var userInternalId: UUID,

        @Column(name = "user_sns_id", length = 50, nullable = false)
        var userSnsId: String,

        @Column(name = "user_name", length = 50, nullable = false)
        var userName: String,

        @Column(name = "user_image", length = 200, nullable = false)
        var userImage: String,

        @Column(name = "user_exp", nullable = false)
        var userExp: Int = 0,

        @Column(name = "user_created_at", nullable = false)
        @Temporal(TemporalType.DATE)
        var createdAt: Date,

        @Column(name = "user_deleted_at", nullable = true)
        @Temporal(TemporalType.DATE)
        var deletedAt: Date? = null,

        @Column(name = "user_is_activate", nullable = false)
        var isActive: Boolean = true
)