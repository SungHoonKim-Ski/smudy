package com.ssafy.data.repository

import com.ssafy.data.datasource.user.remote.UserInfoRemoteDataSource
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.Lyric
import com.ssafy.domain.model.user.Song
import com.ssafy.domain.model.user.Streak
import com.ssafy.domain.model.user.StudyReport
import com.ssafy.domain.model.user.UserInfo
import com.ssafy.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
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

    override suspend fun getHistory(date: Long): Flow<ApiResult<List<StudyReport>>> = buildFlow(
        userInfoRemoteDataSource.getHistory(date), ""
    ) as Flow<ApiResult<List<StudyReport>>>
}