package com.ssafy.userservice.db.postgre.entity

import jakarta.persistence.*

@Entity
@Table(name = "wrong", schema = "public")
class Wrong(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "wrong_id")
        var wrongId: Long,

        @ManyToOne
        @JoinColumn(name = "user_id")
        var user: User,

        @Column(name = "lyric_sentence_en", nullable = false)
        var lyricSentenceEn: String,

        @Column(name = "lyric_sentence_ko", nullable = false)
        var lyricSentenceKo: String
)