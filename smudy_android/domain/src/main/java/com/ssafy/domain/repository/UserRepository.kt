package com.ssafy.domain.repository

import com.ssafy.domain.model.user.UserInfo

interface UserRepository {
    suspend fun getUserInfo(): Result<UserInfo>
}