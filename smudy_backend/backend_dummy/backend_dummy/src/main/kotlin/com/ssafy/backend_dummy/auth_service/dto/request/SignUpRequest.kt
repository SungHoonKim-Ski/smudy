package com.ssafy.backend_dummy.auth_service.dto.request

data class SignUpRequest(

        val userSnsId: String,
        val userSnsName: String,
        val userImage: String
)
