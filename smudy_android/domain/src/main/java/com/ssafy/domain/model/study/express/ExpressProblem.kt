package com.ssafy.domain.model.study.express

data class ExpressProblem(
    val problemBoxId: Int,
    val songArtist: String,
    val songId: String,
    val songName: String,
    val albumJacket: String,
    val problems: List<ExpressQuestionProblem>
)