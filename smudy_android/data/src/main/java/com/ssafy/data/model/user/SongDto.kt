package com.ssafy.data.model.user

import com.squareup.moshi.Json
import com.ssafy.data.model.MappingDto
import com.ssafy.domain.model.user.Song

data class SongDto(
    @Json(name = "spotifyId")
    val id: String,
    @Json(name = "albumJacket")
    val jacket: String,
    @Json(name = "songName")
    val title: String,
    @Json(name = "songArtist")
    val artist: String
): MappingDto<Song> {
    override fun toDomain() = Song(
        id, jacket, title, artist
    )
}