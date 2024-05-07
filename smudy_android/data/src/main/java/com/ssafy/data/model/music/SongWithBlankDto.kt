package com.ssafy.data.model.music

import com.ssafy.data.model.MappingDto
import com.ssafy.domain.model.SongWithBlank

data class SongWithBlankDto(
    val songId: String,
    val songName: String,
    val songArtist: String,
    val albumJacket: String,
    val songDuration: String,
    val lyricEnd: String,
    val lyricsBlank: List<LyricBlankDto>
): MappingDto<SongWithBlank> {
    override fun toDomain() =  SongWithBlank(
        songId, songName, songArtist, albumJacket, songDuration,
        lyricEnd, lyricsBlank.map { it.toDomain() }
    )

}
