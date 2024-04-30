package com.ssafy.backend_auth.dto.request

data class LoginRequest(
    var userSnsName : String,
    var userSnsId : String,
    var userImage: String,
)
