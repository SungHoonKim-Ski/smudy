package com.ssafy.data.model.user

import com.squareup.moshi.Json
import com.ssafy.data.model.MappingDto
import com.ssafy.domain.model.Lyric

data class DailyLyricDto(
    @Json(name = "dailyLyricsEn")
    val lyricsEn: String,
    @Json(name = "dailyLyricsKo")
    val lyricsKo: String
): MappingDto<Lyric>{
    override fun toDomain() = Lyric(
        lyricsEn, lyricsKo
    )
}
