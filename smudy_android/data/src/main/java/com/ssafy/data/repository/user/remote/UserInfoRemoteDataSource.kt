package com.ssafy.data.repository.user.remote

import com.ssafy.data.model.user.UserInfoDto

interface UserInfoRemoteDataSource {
    suspend fun getUserInfo(): Result<UserInfoDto>
}