package com.ssafy.data.model.music

import com.ssafy.data.model.MappingDto
import com.ssafy.domain.model.SubmitFillBlankData

data class SubmitFillBlankResponse(
    val totalSize: Int,
    val score: Int,
    val result: List<SubmitFillBlankResultDto>
): MappingDto<SubmitFillBlankData> {
    override fun toDomain() = SubmitFillBlankData(
        totalSize, score, result.map { it.toDomain() }
    )
}
