package com.ssafy.data.api

import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.LoginInfoRequest
import com.ssafy.data.model.auth.TokenResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {

    @POST("auth/signup")
    suspend fun signup(@Body loginInfoRequest: LoginInfoRequest): Result<DefaultResponse<TokenResponse>>

    @POST("auth/login")
    suspend fun login(@Body loginInfoRequest: LoginInfoRequest): Result<DefaultResponse<TokenResponse>>

    @POST("auth/autologin")
    suspend fun autologin(@Header("Authorization") accessToken: String): Result<DefaultResponse<Boolean>>
}