package com.ssafy.userservice.dto.response.feign

data class PronounceResponse (
        val songId: String,
        val songArtist: String,
        val songName: String,
        val albumJacket: String,
        val lyrics: List<Lyric>
)
