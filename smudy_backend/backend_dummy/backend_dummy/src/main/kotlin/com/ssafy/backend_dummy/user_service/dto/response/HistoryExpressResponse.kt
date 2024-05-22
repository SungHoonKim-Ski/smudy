package com.ssafy.backend_dummy.user_service.dto.response

data class HistoryExpressResponse(
        val userExpresses: MutableList<UserExpress> = mutableListOf()
)
