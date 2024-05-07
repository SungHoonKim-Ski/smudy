package com.ssafy.searchservice.dto.response

data class AutoCreateResponse(
        val songNames: MutableList<SongName> = mutableListOf()
)
