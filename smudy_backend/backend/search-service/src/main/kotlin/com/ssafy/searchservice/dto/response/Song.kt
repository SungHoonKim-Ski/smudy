package com.ssafy.searchservice.dto.response

data class Song(
        val spotifyId: String,
        val albumJacket: String,
        val songName: String,
        val songArtist: String
)
