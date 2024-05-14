package com.ssafy.data.model.music.express.api

import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.auth.RefreshToken
import com.ssafy.data.model.auth.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TokenService {
    @POST("auth/reissue")
    suspend fun reissueToken(@Header("Authorization") accessToken:String, @Body refreshToken: RefreshToken): Response<DefaultResponse<TokenResponse>>
}