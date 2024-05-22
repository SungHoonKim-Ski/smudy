package com.ssafy.searchservice.dto.response

import com.ssafy.searchservice.dto.Song

data class SearchResponse (
        val songs: MutableList<Song> = mutableListOf()
)