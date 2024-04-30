package com.ssafy.data.model.music

data class AddStudyListRequest (
        val songIds: MutableList<SongIdResponse> = mutableListOf()
)