package com.ssafy.userservice.util

enum class CommonResponseCode(val code: Int) {
    SUCCESS(200)
    , FAILED(400)
    , UNAUTHORIZED(401)
}