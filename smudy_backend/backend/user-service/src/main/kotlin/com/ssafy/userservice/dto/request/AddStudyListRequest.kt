package com.ssafy.userservice.dto.request

data class AddStudyListRequest (
        val songIds: MutableList<SongId> = mutableListOf()
)