package com.ssafy.userservice.dto.response

data class InfoResponse (
        val userName: String,
        val userImage: String,
        val userExp: Int,
        val userStudyHistory: MutableList<Song> = mutableListOf()
)