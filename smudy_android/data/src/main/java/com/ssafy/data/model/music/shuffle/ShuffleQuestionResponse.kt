package com.ssafy.data.model.music.shuffle

import com.squareup.moshi.Json
import com.ssafy.data.model.MappingDto
import com.ssafy.domain.model.ShuffleQuestion

data class ShuffleQuestionResponse(
    val problemBoxId: Int,
    val songArtist: String,
    val songId: String,
    val songName: String,
    val albumJacket: String,
    @Json(name = "problemResponses")
    val problems: List<ShuffleQuestionProblemDto>
): MappingDto<ShuffleQuestion> {
    override fun toDomain() = ShuffleQuestion(
        problemBoxId, songArtist, songId, songName, albumJacket, problems.map { it.toDomain() }
    )
}
