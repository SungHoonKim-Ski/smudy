package com.ssafy.data.model.music.fill

import com.ssafy.domain.model.InputAnswer

data class SubmitFillBlankRequest(
    val songId: String,
    val userWords: List<InputAnswer>
)
