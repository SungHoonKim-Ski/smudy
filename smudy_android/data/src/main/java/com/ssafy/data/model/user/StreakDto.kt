package com.ssafy.data.model.user

import com.ssafy.data.model.MappingDto
import com.ssafy.domain.model.user.Streak

data class StreakDto(
    val albumJacket: String,
    val streakDate: Long
): MappingDto<Streak>{
    override fun toDomain()= Streak(
        albumJacket, streakDate
    )

}
