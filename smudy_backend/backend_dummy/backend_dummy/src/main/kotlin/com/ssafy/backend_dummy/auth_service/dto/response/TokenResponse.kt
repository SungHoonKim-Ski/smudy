package com.ssafy.backend_dummy.auth_service.dto.response

data class TokenResponse(
        val grantType: String,
        val accessToken: String,
        val refreshToken: String
)
