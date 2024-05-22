package com.ssafy.data.model.music.shuffle

data class ShuffleSubmitRequest(
    val songId: String,
    val problemBoxId: Int,
    val userPicks: List<UserPickDto>
)
