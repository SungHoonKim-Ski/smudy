package com.ssafy.authservice.entity

import jakarta.persistence.*
import java.util.*


@Entity(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Long = 0, // Database primary key

    val userInternalId: String, // Unique internal ID

    val userSnsId: String, // OAuth-provided ID
    val userName: String, // OAuth-provided name
    val userImage: String, // OAuth-provided image

    @Enumerated(EnumType.STRING)
    val userRole: Role // User's role
)