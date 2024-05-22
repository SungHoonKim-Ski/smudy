package com.ssafy.backend_dummy.user_service.dto.response

data class Streak (
        var albumJacket: String = "https://smudy.s3.ap-northeast-2.amazonaws.com/songs/albumart.jpg",
        val streakDate: Long
)