package com.ssafy.backend_dummy.search_service.dto.response

data class Song(
        val spotifyId: String,
        val albumJacket: String,
        val songName: String,
        val songArtist: String
)
