package com.ssafy.data.mapper

import com.ssafy.data.model.music.StudyListResponse
import com.ssafy.domain.model.Study

fun StudyListResponse.toStudy(): List<Study> {
    return this.userStudyList.map {
        Study(it.spotifyId, it.albumJacket, it.songName, it.songArtist)
    }
}