package com.ssafy.data.model.music.shuffle

import com.ssafy.data.model.MappingDto
import com.ssafy.domain.model.ShuffleSubmitResult

data class ShuffleSubmitResponse (
    val totalSize: Int,
    val score: Int,
    val correct: List<ShuffleQuestionProblemDto>,
    val wrong: List<ShuffleQuestionProblemDto>
) : MappingDto<ShuffleSubmitResult> {
    override fun toDomain() = ShuffleSubmitResult(
        totalSize, score, correct.map { it.toDomain() }, wrong.map { it.toDomain() }
    )
}