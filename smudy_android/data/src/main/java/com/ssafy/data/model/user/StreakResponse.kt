package com.ssafy.data.model.user

import com.ssafy.data.model.MappingDto
import com.ssafy.domain.model.user.Streak

data class StreakResponse(
    val streaks: List<StreakDto>
): MappingDto<List<Streak>>{
    override fun toDomain(): List<Streak>{
        return streaks.map { it.toDomain() }
    }

}
