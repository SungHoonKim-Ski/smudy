package com.ssafy.data.model.music.express

import com.squareup.moshi.Json

data class ExpressProblemResponse(
    val problemBoxId: Int,
    val songArtist: String,
    val songId: String,
    val songName: String,
    val albumJacket: String,
    @Json(name = "problemResponses")
    val problems: List<ExpressQuestionProblemResponse>
)