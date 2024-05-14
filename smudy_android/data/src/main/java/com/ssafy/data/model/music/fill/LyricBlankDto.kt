package com.ssafy.data.model.music.fill

import com.ssafy.data.model.MappingDto
import com.ssafy.domain.model.LyricBlank

data class LyricBlankDto(
    val lyricBlank: String,
    val lyricBlankAnswer: String,
    val lyricStartTimeStamp: String
): MappingDto<LyricBlank> {
    override fun toDomain() = LyricBlank(
        lyricBlank, lyricBlankAnswer, lyricStartTimeStamp
    )
}
