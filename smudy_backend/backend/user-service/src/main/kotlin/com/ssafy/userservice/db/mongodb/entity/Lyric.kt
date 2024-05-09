package com.ssafy.userservice.db.mongodb.entity

import jakarta.persistence.Embeddable
import org.springframework.data.mongodb.core.mapping.Field

@Embeddable
data class Lyric(
        @Field(name = "lyric_timestamp")
        var lyricTimestamp: String,

        @Field(name = "lyric_sentence_en")
        var lyricSentenceEn: String = "",

        @Field(name = "lyric_blank")
        var lyricBlank: String = "",

        @Field(name = "lyric_blank_answer")
        var lyricBlankAnswer: String = ""
)