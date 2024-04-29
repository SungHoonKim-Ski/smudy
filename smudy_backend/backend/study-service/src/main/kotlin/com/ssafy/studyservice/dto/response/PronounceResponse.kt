package com.ssafy.studyservice.dto.response

data class PronounceResponse (
        val songId: String,
        val songArtist: String,
        val songName: String,
        val albumJacket: String,
        val lyrics: MutableList<Lyric> = mutableListOf()
)
