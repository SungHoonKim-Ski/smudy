package com.ssafy.studyservice.dto.response

data class SubmitPickResponse (
        val totalSize: Int = 5,
        val score: Int,
        val correct: MutableList<Problem> = mutableListOf(),
        val wrong: MutableList<WrongProblem> = mutableListOf()
)
