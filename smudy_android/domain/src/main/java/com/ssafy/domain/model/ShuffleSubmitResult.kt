package com.ssafy.domain.model

data class ShuffleSubmitResult(
    val totalSize: Int,
    val score: Int,
    val correct: List<ShuffleQuestionProblem>,
    val wrong: List<ShuffleQuestionProblem>
)