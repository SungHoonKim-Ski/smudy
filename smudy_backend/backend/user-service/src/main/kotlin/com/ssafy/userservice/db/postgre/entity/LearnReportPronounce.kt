package com.ssafy.userservice.db.postgre.entity

import com.ssafy.userservice.config.ObjectMapperConfig
import com.ssafy.userservice.dto.response.ai.LyricAiAnalyze
import com.ssafy.userservice.util.LyricAIConverter
import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.*
import org.hibernate.annotations.Type

@Entity
@Table(name = "learn_report_pronounce", schema = "public")
class LearnReportPronounce(

        @Id
        @Column(name = "learn_report_id")
        var learnReportId: Int,

        @Column(name = "learn_report_pronounce_stt_en", nullable = false)
        var learnReportPronounceSttEn: String,

        @Column(name = "learn_report_pronounce_stt_ko", nullable = false)
        var learnReportPronounceSttKo: String,

        @Column(name = "lyric_sentence_en", nullable = false)
        var lyricSentenceEn: String,

        @Column(name = "lyric_sentence_ko", nullable = false)
        var lyricSentenceKo: String,

        @Column(name = "learn_report_user_pronounce", nullable = false)
        var learnReportUserPronounce: String,

        @Column(name = "learn_report_tts_pronounce", nullable = false)
        var learnReportTtsPronounce: String,

        @Convert(converter = LyricAIConverter::class)
        @Column(name = "lyric_ai_analyze", columnDefinition = "jsonb", nullable = false)
        var lyricAiAnalyze: LyricAiAnalyze
)