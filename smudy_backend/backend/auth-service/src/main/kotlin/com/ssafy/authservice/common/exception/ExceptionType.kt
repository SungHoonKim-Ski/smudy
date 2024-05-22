package com.ssafy.authservice.common.exception

enum class ExceptionType(
    val code: Int, val message: String
) {

    // 회원가입
    DUPLICATE_USER_EXCEPTION(409, "이미 가입된 계정입니다."),
    USER_NOT_FOUND_EXCEPTION(404, "해당하는 유저를 찾을 수 없습니다."),

    // Authorization 해더
    INVAILD_TOKEN_EXCEPTION(401, "Access Token을 파싱할 수 없습니다.")

}