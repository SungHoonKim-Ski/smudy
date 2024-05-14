package com.ssafy.data.model.music.shuffle

import com.ssafy.data.model.MappingDto
import com.ssafy.domain.model.ShuffleQuestionProblem

data class ShuffleQuestionProblemDto(
    val lyricSentenceEn: String,
    val lyricSentenceKo: String
): MappingDto<ShuffleQuestionProblem> {
    override fun toDomain() = ShuffleQuestionProblem(
        lyricSentenceEn, lyricSentenceKo
    )
}
