package com.ssafy.domain.model

data class SongWithBlank(
    val songId: String,
    val songName: String,
    val songArtist: String,
    val albumJacket: String,
    val songDuration: String,
    val lyricEnd: String,
    val lyricsBlank: List<LyricBlank>
)