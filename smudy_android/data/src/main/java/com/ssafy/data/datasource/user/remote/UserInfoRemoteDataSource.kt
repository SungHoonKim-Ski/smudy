package com.ssafy.data.datasource.user.remote

import com.ssafy.data.model.user.HistoryResponse
import com.ssafy.data.model.user.RecommendedSongsResponse
import com.ssafy.data.model.user.StreakDto
import com.ssafy.data.model.user.StreakResponse
import com.ssafy.data.model.user.UserInfoDto
import com.ssafy.data.model.user.WrongLyricDto

interface UserInfoRemoteDataSource {
    suspend fun getUserInfo(): Result<UserInfoDto>
    suspend fun getStreak(): Result<StreakResponse>
    suspend fun getWrongLyric(): Result<WrongLyricDto>
    suspend fun getRecommendedSongs(): Result<RecommendedSongsResponse>
    suspend fun getHistory(date: Long): Result<HistoryResponse>
}