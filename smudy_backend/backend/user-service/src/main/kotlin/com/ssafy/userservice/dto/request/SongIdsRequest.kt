package com.ssafy.userservice.dto.request

data class SongIdsRequest (
        val songIds: MutableList<SongId> = mutableListOf()
)