package com.ssafy.userservice.dto.request

import com.ssafy.userservice.dto.response.UserExpress

data class SubmitExpressRequest(
        val problemBoxId: Int,
        val songId: String,
        val userExpresses: List<UserExpress>
)
