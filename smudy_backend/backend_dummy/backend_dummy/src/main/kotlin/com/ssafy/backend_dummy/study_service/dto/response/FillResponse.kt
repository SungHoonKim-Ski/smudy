package com.ssafy.backend_dummy.study_service.dto.response

data class FillResponse (
        val songId: String,
        val songArtist: String,
        val songName: String,
        val albumJacket: String,
        val songDuration: String,
        val lyricEnd: String,
        val lyricsBlank: MutableList<LyricBlank> = mutableListOf()
)
