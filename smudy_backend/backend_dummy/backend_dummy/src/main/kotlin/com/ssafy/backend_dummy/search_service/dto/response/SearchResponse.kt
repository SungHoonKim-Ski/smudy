package com.ssafy.backend_dummy.search_service.dto.response

data class SearchResponse (
        val songs: MutableList<Song> = mutableListOf()
)