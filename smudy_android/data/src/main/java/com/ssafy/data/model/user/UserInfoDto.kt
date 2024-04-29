package com.ssafy.data.model.user

import com.squareup.moshi.Json

data class UserInfoDto(
    @Json(name = "userName")
    val name: String,
    @Json(name = "userImage")
    val img: String,
    @Json(name = "userExp")
    val exp: Int,
    @Json(name = "userStudyHistory")
    val history: List<HistoryDto>
)
