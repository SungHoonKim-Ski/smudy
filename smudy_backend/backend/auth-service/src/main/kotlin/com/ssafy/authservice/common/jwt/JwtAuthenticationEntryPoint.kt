package com.ssafy.authservice.common.jwt

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

// 인증(Authentication) 실패 시 실행
@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {

    // Filter 과정에서 인증 실패시 발생
    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
        response.characterEncoding = "utf-8"
        response.sendError(401, "잘못된 접근입니다.")
    }
}