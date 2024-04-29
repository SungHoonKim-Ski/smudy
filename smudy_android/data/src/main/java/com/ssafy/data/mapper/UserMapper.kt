package com.ssafy.data.mapper

import com.ssafy.data.model.user.HistoryDto
import com.ssafy.data.model.user.UserInfoDto
import com.ssafy.domain.model.user.History
import com.ssafy.domain.model.user.UserInfo

fun UserInfoDto.toDomain() = UserInfo(
    name, img, exp, history.map { it.toDomain() }
)
fun HistoryDto.toDomain() = History(
    id, jacket, title, artist
)