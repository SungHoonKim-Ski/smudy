package com.ssafy.data.api

import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.auth.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("api/auth/signup")
    suspend fun signup(@Body loginInfoRequest: LoginInfoRequest): Result<DefaultResponse<TokenResponse>>

    @POST("api/auth/login")
    suspend fun login(@Body loginInfoRequest: LoginInfoRequest): Result<DefaultResponse<TokenResponse>>


    @POST("api/auth/autologin")
    suspend fun autologin()

    @POST("api/auth/reissue")
    suspend fun reissue()
}