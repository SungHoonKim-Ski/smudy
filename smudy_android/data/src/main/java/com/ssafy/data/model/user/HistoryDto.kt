package com.ssafy.data.model.user

import com.squareup.moshi.Json

data class HistoryDto (
    @Json(name = "spotifyId")
    val id: String,
    @Json(name = "albumJacket")
    val jacket: String,
    @Json(name = "songName")
    val title: String,
    @Json(name = "songArtist")
    val artist: String
)