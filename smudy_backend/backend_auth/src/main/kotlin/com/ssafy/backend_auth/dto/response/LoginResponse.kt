package com.ssafy.backend_auth.dto.response

data class LoginResponse(
    val code: Int,
    val message: String,
    val data: Data
)

data class Data(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String
)