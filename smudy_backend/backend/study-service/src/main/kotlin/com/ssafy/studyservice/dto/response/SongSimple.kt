package com.ssafy.studyservice.dto.response

data class SongSimple(
        val spotifyId: String,
        val albumJacket: String,
        val songName: String,
        val songArtist: String
)
