package com.ssafy.backend_dummy.user_service.dto.response

data class StreakResponse (
        val streaks: MutableList<Streak> = mutableListOf()
)