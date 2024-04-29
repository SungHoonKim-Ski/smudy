package com.ssafy.domain.model.user

data class UserInfo(
    val name: String,
    val img: String,
    val exp: Int,
    val history: List<History>
)
