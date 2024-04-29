package com.ssafy.data.repository.user.remote

import com.ssafy.data.api.UserService
import com.ssafy.data.mapper.toNonDefault
import com.ssafy.data.model.user.UserInfoDto
import javax.inject.Inject

class UserInfoRemoteDataSourceImpl @Inject constructor(
    private val userService: UserService
): UserInfoRemoteDataSource {
    override suspend fun getUserInfo(): Result<UserInfoDto> {
        return userService.getUserInfo().toNonDefault()
    }
}