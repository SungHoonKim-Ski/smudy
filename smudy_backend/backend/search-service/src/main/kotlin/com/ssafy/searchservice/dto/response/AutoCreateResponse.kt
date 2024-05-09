package com.ssafy.searchservice.dto.response

import com.ssafy.searchservice.dto.SongName

data class AutoCreateResponse(
        val songNames: MutableList<SongName> = mutableListOf()
)
