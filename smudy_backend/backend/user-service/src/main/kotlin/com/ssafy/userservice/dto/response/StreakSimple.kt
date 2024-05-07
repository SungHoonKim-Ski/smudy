package com.ssafy.userservice.dto.response

data class StreakSimple (
        var albumJacket: String = "https://smudy.s3.ap-northeast-2.amazonaws.com/songs/albumart.jpg",
        val streakDate: Long
)