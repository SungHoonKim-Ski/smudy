package com.ssafy.searchservice.dto.response

data class RecommendResponse (
        val userRecommendSongs: MutableList<Song> = mutableListOf()
)
