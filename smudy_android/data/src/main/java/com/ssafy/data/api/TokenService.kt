package com.ssafy.data.api

import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.auth.TokenResponse
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface TokenService {
    @POST("api/auth/reissue")
    suspend fun reissueToken(@Header("Authorization") refreshToken: String): Response<DefaultResponse<TokenResponse>>
}