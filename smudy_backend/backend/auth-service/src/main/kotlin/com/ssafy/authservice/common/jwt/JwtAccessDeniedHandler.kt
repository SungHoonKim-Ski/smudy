package com.ssafy.authservice.common.jwt

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

// 인가(Authorization) 실패한 경우 실행
@Component
class JwtAccessDeniedHandler : AccessDeniedHandler {

    // 해당 USER_ROLE 이 접근할 수 없는 경로인 경우 발생
    override fun handle(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            accessDeniedException: AccessDeniedException?) {

        response?.characterEncoding = "utf-8"
        response?.sendError(403, "접근 권한이 없습니다.")
    }
}