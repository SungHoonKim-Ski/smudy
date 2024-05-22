package com.ssafy.data.model.music.pronounce

import com.ssafy.domain.model.study.pronounce.PronounceProblemInfo

data class PronounceResponse(
    val songId: String,
    val songArtist: String,
    val songName: String,
    val albumJacket: String,
    val lyrics: List<LyricResponse>
) {
    fun toDomain(): PronounceProblemInfo =
        PronounceProblemInfo(
            this.songId,
            this.songArtist,
            this.songName,
            this.albumJacket,
            this.lyrics.map { it.lyric }
        )
}
