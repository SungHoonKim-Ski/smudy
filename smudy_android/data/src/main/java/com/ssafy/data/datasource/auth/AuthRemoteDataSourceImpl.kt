package com.ssafy.data.datasource.auth

import com.ssafy.data.api.AuthService
import com.ssafy.data.model.LoginInfoRequest
import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.auth.TokenResponse
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthRemoteDataSource {
    override suspend fun signup(loginInfoRequest: LoginInfoRequest): Result<DefaultResponse<TokenResponse>> {
        return authService.signup(loginInfoRequest)
    }

    override suspend fun login(loginInfoRequest: LoginInfoRequest): Result<DefaultResponse<TokenResponse>> {
        return authService.login(loginInfoRequest)
    }

    override suspend fun autoLogin(): Result<DefaultResponse<Boolean>> {
        return authService.autologin()
    }
}