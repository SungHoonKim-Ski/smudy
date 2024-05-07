package com.ssafy.userservice.db.postgre.entity

import jakarta.persistence.*

@Entity
@Table(name = "studylist", schema = "public")
class StudyList(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "studylist_id")
        var studyListId: Int,

        @ManyToOne
        @JoinColumn(name = "user_id")
        var user: User,

        @Column(name = "song_id", length = 30, nullable = false)
        var songId: String = ""
)