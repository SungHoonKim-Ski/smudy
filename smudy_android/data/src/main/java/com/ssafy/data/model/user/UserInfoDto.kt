package com.ssafy.data.model.user

import com.squareup.moshi.Json
import com.ssafy.data.model.MappingDto
import com.ssafy.domain.model.user.UserInfo

data class UserInfoDto(
    @Json(name = "userName")
    val name: String,
    @Json(name = "userImage")
    val img: String,
    @Json(name = "userExp")
    val exp: Int,
    @Json(name = "userStudyHistory")
    val history: List<SongDto>
): MappingDto<UserInfo>{
    override fun toDomain() = UserInfo(
        name, img, exp, history.map { it.toDomain() }
    )

}
