package com.ssafy.data.model.music.pronounce

import com.squareup.moshi.Json

data class TimestampResponse(
    @Json(name = "end_time")
    val endTime: Double,
    @Json(name = "start_time")
    val startTime: Double,
    val word: String
)