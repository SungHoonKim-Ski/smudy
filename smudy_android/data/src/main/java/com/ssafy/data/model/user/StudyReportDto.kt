package com.ssafy.data.model.user

import com.squareup.moshi.Json
import com.ssafy.data.model.MappingDto
import com.ssafy.domain.model.user.StudyReport

data class StudyReportDto(
    @Json(name = "learnReportId")
    val id: Long,
    @Json(name = "albumJacket")
    val jacket: String,
    @Json(name = "songName")
    val title: String,
    @Json(name = "songArtist")
    val artist: String,
    @Json(name = "problemType")
    val type: String,
    @Json(name = "learnReportDate")
    val date: Long
): MappingDto<StudyReport> {
    override fun toDomain() = StudyReport(
        id, jacket, title, artist, type, date
    )
}
