package com.ssafy.data.model.user

import com.ssafy.data.model.MappingDto
import com.ssafy.domain.model.user.Song

data class RecommendedSongsResponse(
    val userRecommendSongs: List<SongDto>
) : MappingDto<List<Song>> {
    override fun toDomain(): List<Song> {
        return userRecommendSongs.map { it.toDomain() }
    }
}