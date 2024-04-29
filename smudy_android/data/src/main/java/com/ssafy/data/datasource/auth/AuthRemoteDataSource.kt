package com.ssafy.data.datasource.auth

import com.ssafy.data.api.LoginInfoRequest
import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.auth.TokenResponse

interface AuthRemoteDataSource {
    suspend fun signup(loginInfoRequest: LoginInfoRequest): Result<DefaultResponse<TokenResponse>>
    suspend fun login(loginInfoRequest: LoginInfoRequest): Result<DefaultResponse<TokenResponse>>
    suspend fun autoLogin(accessToken: String): Result<DefaultResponse<Boolean>>
}