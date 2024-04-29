package com.ssafy.backend_dummy.user_service.dto.response

data class StudyListResponse (
        val userStudyList: MutableList<Song> = mutableListOf()
)