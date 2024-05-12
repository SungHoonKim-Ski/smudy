package com.ssafy.data.model.music

import com.ssafy.domain.model.InputAnswer

data class SubmitFillBlankRequest(
    val songId: String,
    val userWords: List<InputAnswer>
)
