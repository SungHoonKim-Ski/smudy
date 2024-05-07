package com.ssafy.studyservice.db.mongodb.entity

import org.springframework.data.mongodb.core.mapping.Field

class Lyric(

        @Field(name = "lyric_timestamp")
        var lyricTimestamp: String,

        @Field(name = "lyric_sentence_en")
        var lyricSentenceEn: String,

        @Field(name = "lyric_blank")
        var lyricBlank: String,

        @Field(name = "lyric_blank_answer")
        var lyricBlankAnswer: String
)