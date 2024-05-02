package com.ssafy.data.datasource.user.remote

import com.ssafy.data.api.UserService
import com.ssafy.data.mapper.toNonDefault
import com.ssafy.data.model.user.RecommendedSongsResponse
import com.ssafy.data.model.user.StreakDto
import com.ssafy.data.model.user.StreakResponse
import com.ssafy.data.model.user.UserInfoDto
import com.ssafy.data.model.user.WrongLyricDto
import javax.inject.Inject

class UserInfoRemoteDataSourceImpl @Inject constructor(
    private val userService: UserService
): UserInfoRemoteDataSource {
    override suspend fun getUserInfo(): Result<UserInfoDto> {
        return userService.getUserInfo().toNonDefault()
    }

    override suspend fun getStreak(): Result<StreakResponse> {
        return userService.getStreak().toNonDefault()
    }

    override suspend fun getWrongLyric(): Result<WrongLyricDto> {
        return userService.getWrong().toNonDefault()
    }

    override suspend fun getRecommendedSongs(): Result<RecommendedSongsResponse> {
        return userService.getRecommendedSong().toNonDefault()
    }
}