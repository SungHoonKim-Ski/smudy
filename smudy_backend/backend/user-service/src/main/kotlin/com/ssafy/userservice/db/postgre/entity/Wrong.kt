package com.ssafy.userservice.db.postgre.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "wrong", schema = "public")
class Wrong(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "wrong_id")
        var wrongId: Long,

//        @ManyToOne
        @JoinColumn(name = "user_internal_id")
        var userInternalId: UUID,

        @Column(name = "lyric_sentence_en", nullable = false)
        var lyricSentenceEn: String,

        @Column(name = "lyric_sentence_ko", nullable = false)
        var lyricSentenceKo: String
)