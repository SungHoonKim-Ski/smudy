package com.ssafy.backend_dummy.user_service.dto.response

data class InfoResponse (
        val userName: String,
        val userImage: String,
        val userExp: Int,
        val userStudyHistory: MutableList<Song> = mutableListOf()
)