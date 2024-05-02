package com.ssafy.authservice.common.jwt

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

// 인가(Authorization) 실패 시 실행
@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
        response?.characterEncoding = "utf-8"
        response?.sendError(401, "잘못된 접근입니다.")
    }
}