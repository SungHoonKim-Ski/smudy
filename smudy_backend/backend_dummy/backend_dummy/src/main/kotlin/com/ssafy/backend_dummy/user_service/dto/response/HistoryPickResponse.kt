package com.ssafy.backend_dummy.user_service.dto.response

data class HistoryPickResponse (
        val totalSize: Int = 5,
        val score: Int,
        val correct: MutableList<Problem> = mutableListOf(),
        val wrong: MutableList<WrongProblem> = mutableListOf()
)
