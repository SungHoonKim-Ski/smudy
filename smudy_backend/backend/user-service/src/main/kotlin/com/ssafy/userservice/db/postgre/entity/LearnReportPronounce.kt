package com.ssafy.userservice.db.postgre.entity

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.ssafy.userservice.db.postgre.entity.ai.EntityLyricAiAnalyze
import com.ssafy.userservice.dto.response.ai.LyricAiAnalyze
import com.ssafy.userservice.util.LyricAIConverter
import jakarta.persistence.*

@Entity
@Table(name = "learn_report_pronounce", schema = "public")
class LearnReportPronounce(

        @Id
        @Column(name = "learn_report_id")
        var learnReportId: Int,

        @Column(name = "learn_report_pronounce_user_en", nullable = false)
        var learnReportPronounceUserEn: String,

//        @Column(name = "learn_report_pronounce_stt_ko", nullable = false)
//        var learnReportPronounceSttKo: String,

        @Column(name = "lyric_sentence_en", nullable = false)
        var lyricSentenceEn: String,

        @Column(name = "lyric_sentence_ko", nullable = false)
        var lyricSentenceKo: String,

        @Column(name = "lyric_ai_analyze", nullable = false)
        var lyricAiAnalyze: String,
)