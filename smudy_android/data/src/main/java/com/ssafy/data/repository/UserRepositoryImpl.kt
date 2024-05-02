package com.ssafy.data.repository

import com.ssafy.data.datasource.user.remote.UserInfoRemoteDataSource
import com.ssafy.data.model.MappingDto
import com.ssafy.data.model.user.StreakDto
import com.ssafy.domain.model.ApiError
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.Lyric
import com.ssafy.domain.model.user.Song
import com.ssafy.domain.model.user.Streak
import com.ssafy.domain.model.user.UserInfo
import com.ssafy.domain.repository.UserRepository
import com.ssafy.util.NetworkException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class UserRepositoryImpl @Inject constructor(
   private val userInfoRemoteDataSource: UserInfoRemoteDataSource
): UserRepository {

    override suspend fun getUserInfo() =  buildFlow(
        userInfoRemoteDataSource.getUserInfo(), ""
    ) as Flow<ApiResult<UserInfo>>

    override suspend fun getStreak() = buildFlow(
        userInfoRemoteDataSource.getStreak(), ""
    ) as Flow<ApiResult<List<Streak>>>

    override suspend fun getWrongLyric() = buildFlow(
        userInfoRemoteDataSource.getWrongLyric(), ""
    ) as Flow<ApiResult<Lyric>>

    override suspend fun getRecommendedSongs() =  buildFlow(
        userInfoRemoteDataSource.getRecommendedSongs(), ""
    ) as Flow<ApiResult<List<Song>>>
}