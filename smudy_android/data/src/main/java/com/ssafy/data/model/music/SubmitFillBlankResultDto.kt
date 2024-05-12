package com.ssafy.data.model.music

import com.ssafy.data.model.MappingDto
import com.ssafy.domain.model.SubmitFillBlankResult

data class SubmitFillBlankResultDto(
    val lyricBlank: String,
    val originWord: String,
    val userWord: String,
    val isCorrect: Boolean
): MappingDto<SubmitFillBlankResult> {
    override fun toDomain() = SubmitFillBlankResult(
        lyricBlank, originWord, userWord, isCorrect
    )
}
