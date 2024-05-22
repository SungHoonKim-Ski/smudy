package com.ssafy.studyservice.db.postgre.entity

import jakarta.persistence.*

@Entity
@Table(name = "daily_lyrics")
class DailyLyrics(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "daily_lyrics_id", nullable = false)
        val id: Int,

        @Column(name = "song_id", nullable = false, length = 30)
        val songId: String,

        @Column(name = "daily_lyrics_date", nullable = false)
        val date: java.sql.Date,

        @Column(name = "daily_lyrics_en", nullable = false, columnDefinition = "TEXT")
        val lyricsEn: String,

        @Column(name = "daily_lyrics_ko", nullable = false, columnDefinition = "TEXT")
        val lyricsKo: String
)
