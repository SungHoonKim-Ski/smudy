package com.ssafy.backend_dummy.user_service.dto.response

data class HistoryResponse (
        val learnReports: MutableList<LearnReport> = mutableListOf()
)