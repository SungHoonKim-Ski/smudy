package com.ssafy.data.repository

import com.ssafy.data.mapper.toDomain
import com.ssafy.data.repository.user.remote.UserInfoRemoteDataSource
import com.ssafy.domain.model.user.UserInfo
import com.ssafy.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
   private val userInfoRemoteDataSource: UserInfoRemoteDataSource
): UserRepository {
    override suspend fun getUserInfo(): Result<UserInfo> {
        return userInfoRemoteDataSource.getUserInfo().map {
            it.toDomain()
        }
    }
}