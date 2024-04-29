package com.ssafy.data.api

import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.user.UserInfoDto
import retrofit2.http.GET

interface UserService {
    @GET("user/info")
    suspend fun getUserInfo(): Result<DefaultResponse<UserInfoDto>>
}