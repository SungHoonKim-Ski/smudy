package com.ssafy.data.model.music.pronounce

import com.squareup.moshi.Json

data class FormantsAvgResponse(
    @Json(name = "F1")
    val f1: List<Double>,
    @Json(name = "F2")
    val f2: List<Double>
)