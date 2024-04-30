package com.ssafy.data.datasource.user.remote

import com.ssafy.data.model.user.UserInfoDto

interface UserInfoRemoteDataSource {
    suspend fun getUserInfo(): Result<UserInfoDto>
}