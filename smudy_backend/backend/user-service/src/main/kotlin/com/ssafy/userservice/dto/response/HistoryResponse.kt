package com.ssafy.userservice.dto.response

data class HistoryResponse (
        val learnReports: MutableList<LearnReport> = mutableListOf()
)