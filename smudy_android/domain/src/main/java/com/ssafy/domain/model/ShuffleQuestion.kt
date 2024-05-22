package com.ssafy.domain.model

data class ShuffleQuestion(
    val problemBoxId: Int,
    val songArtist: String,
    val songId: String,
    val songName: String,
    val albumJacket: String,
    val problems: List<ShuffleQuestionProblem>
)