package com.ssafy.backend_dummy.search_service.dto.response

data class AutoCreateResponse(
        val songNames: MutableList<SongName> = mutableListOf()
)
