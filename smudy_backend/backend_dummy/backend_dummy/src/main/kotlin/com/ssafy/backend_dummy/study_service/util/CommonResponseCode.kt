package com.ssafy.backend_dummy.study_service.util

enum class CommonResponseCode(val code: Int) {
    SUCCESS(200)
    , FAILED(400)
    , UNAUTHORIZED(401)
}