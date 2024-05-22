package com.ssafy.data.model.auth

data class TokenResponse(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String
)