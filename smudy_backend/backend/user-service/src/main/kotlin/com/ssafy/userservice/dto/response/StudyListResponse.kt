package com.ssafy.userservice.dto.response

data class StudyListResponse (
        val userStudyList: MutableList<SongSimple> = mutableListOf()
)