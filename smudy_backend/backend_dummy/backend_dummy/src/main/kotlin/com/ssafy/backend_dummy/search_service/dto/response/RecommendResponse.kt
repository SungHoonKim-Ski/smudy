package com.ssafy.backend_dummy.search_service.dto.response

data class RecommendResponse (
        val userRecommendSongs: MutableList<Song> = mutableListOf()
)
