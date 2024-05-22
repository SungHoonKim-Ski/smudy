package com.ssafy.userservice.dto.response.feign

data class FillResponse (
        val songId: String,
        val songArtist: String,
        val songName: String,
        val albumJacket: String,
        val songDuration: String,
        val lyricEnd: String,
        val lyricsBlank: List<LyricBlank>
)
