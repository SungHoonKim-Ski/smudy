package com.ssafy.userservice.dto.response

data class RecommendResponse (
        val userRecommendSongs: MutableList<SongSimple> = mutableListOf()
)
