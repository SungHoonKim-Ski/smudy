package com.ssafy.data.model.music

data class AutoCreateResponse(
        val songNames: MutableList<SongNameResponse> = mutableListOf()
)
