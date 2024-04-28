package com.ssafy.backend_dummy.user_service.dto.request

data class AddStudyListRequest (
        val songIds: MutableList<SongId> = mutableListOf()
)