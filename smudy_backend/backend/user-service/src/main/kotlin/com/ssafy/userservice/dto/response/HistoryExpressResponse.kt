package com.ssafy.userservice.dto.response

data class HistoryExpressResponse(
        val userExpresses: MutableList<UserExpress> = mutableListOf()
)
