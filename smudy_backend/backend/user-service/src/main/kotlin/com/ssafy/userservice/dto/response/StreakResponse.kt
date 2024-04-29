package com.ssafy.userservice.dto.response

data class StreakResponse (
        val streaks: MutableList<Streak> = mutableListOf()
)