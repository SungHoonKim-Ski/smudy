package com.ssafy.domain.repository

import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.Lyric
import com.ssafy.domain.model.user.Song
import com.ssafy.domain.model.user.Streak
import com.ssafy.domain.model.user.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserInfo(): Flow<ApiResult<UserInfo>>
    suspend fun getStreak(): Flow<ApiResult<List<Streak>>>

    suspend fun getWrongLyric(): Flow<ApiResult<Lyric>>

    suspend fun getRecommendedSongs(): Flow<ApiResult<List<Song>>>

}