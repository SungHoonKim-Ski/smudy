package com.ssafy.searchservice.dto.response

data class SearchResponse (
        val songs: MutableList<Song> = mutableListOf()
)