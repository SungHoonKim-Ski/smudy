package com.ssafy.authservice.dto.response

data class TokenResponse(
        val grantType: String,
        val accessToken: String,
        val refreshToken: String
)
