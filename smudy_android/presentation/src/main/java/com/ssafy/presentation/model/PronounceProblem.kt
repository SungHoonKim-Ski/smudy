package com.ssafy.presentation.model

data class PronounceProblem(
    val songId: String,
    val songArtist: String,
    val songName: String,
    val albumJacket: String,
    val lyrics: List<String>
)
