package com.ssafy.data.model.music.shuffle

import com.ssafy.data.model.MappingDto

data class ShuffleSubmitResponse (
    val totalSize: Int,
    val score: Int,
    val correct: List<ShuffleQuestionProblemDto>,
    val wrong: List<ShuffleQuestionProblemDto>
)