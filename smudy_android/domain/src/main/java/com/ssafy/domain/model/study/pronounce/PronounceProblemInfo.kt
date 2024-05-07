package com.ssafy.domain.model.study.pronounce

data class PronounceProblemInfo(
    val songId: String,
    val songArtist: String,
    val songName: String,
    val albumJacket: String,
    val lyrics: List<String>
)
