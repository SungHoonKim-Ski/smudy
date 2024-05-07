package com.ssafy.userservice.dto.response

data class SubmitPickResponse (
        val totalSize: Int = 5,
        var score: Int = 0,
        val correct: MutableList<ProblemResponse> = mutableListOf(),
        val wrong: MutableList<WrongProblem> = mutableListOf()
)
