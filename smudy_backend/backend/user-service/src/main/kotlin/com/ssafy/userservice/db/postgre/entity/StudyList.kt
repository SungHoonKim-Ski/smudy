package com.ssafy.userservice.db.postgre.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "studylist", schema = "public")
class StudyList(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "studylist_id")
        var studyListId: Int,

//        @ManyToOne
        @JoinColumn(name = "user_internal_id")
        var userInternalId: UUID,

        @Column(name = "song_id", length = 30, nullable = false)
        var songId: String
)