package com.ssafy.userservice.db.postgre.entity

import jakarta.persistence.*

@Entity
@Table(name = "learn_report_pronounce", schema = "public")
class LearnReportPronounce(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "learn_report_id")
        var learnReportId: Int,

        @ManyToOne
        @MapsId
        @JoinColumn(name = "learn_report_id")
        var learnReport: LearnReport,

        @Column(name = "learn_report_pronounce_stt_user", nullable = false)
        var learnReportPronounceSttUser: String,

        @Column(name = "learn_report_pronounce_stt_ko", nullable = false)
        var learnReportPronounceSttKo: String,

        @Column(name = "lyric_sentence_en", nullable = false)
        var lyricSentenceEn: String,

        @Column(name = "lyric_sentence_ko", nullable = false)
        var lyricSentenceKo: String,

        @Column(name = "lyric_ai_analyze", nullable = false)
        var lyricAiAnalyze: String
)