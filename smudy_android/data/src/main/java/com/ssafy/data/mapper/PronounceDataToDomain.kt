package com.ssafy.data.mapper

import com.ssafy.data.model.music.pronounce.PronounceResponse
import com.ssafy.domain.model.study.pronounce.PronounceProblemInfo

fun PronounceResponse.toPronounceProblemInfo(): PronounceProblemInfo =
    PronounceProblemInfo(
        this.songId,
        this.songArtist,
        this.songName,
        this.albumJacket,
        this.lyrics.map { it.lyric }
    )