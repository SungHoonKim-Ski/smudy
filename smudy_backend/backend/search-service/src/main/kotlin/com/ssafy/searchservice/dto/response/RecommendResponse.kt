package com.ssafy.searchservice.dto.response

import com.ssafy.searchservice.dto.Song

data class RecommendResponse (
        val userRecommendSongs: MutableList<Song> = mutableListOf()
)
