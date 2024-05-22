package com.ssafy.data.model.user

import com.squareup.moshi.Json
import com.ssafy.data.model.MappingDto
import com.ssafy.domain.model.user.StudyReport

data class HistoryResponse(
    @Json(name = "learnReportResponses")
    val learnReports: List<StudyReportDto>
): MappingDto<List<StudyReport>> {
    override fun toDomain(): List<StudyReport> {
        return learnReports.map { it.toDomain() }
    }
}
