package com.ssafy.authservice.dto.request

data class SignUpRequest(

        val userSnsId: String,
        val userSnsName: String,
        val userImage: String
)
