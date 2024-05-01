package com.ssafy.authservice.common.jwt

import io.jsonwebtoken.IncorrectClaimException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    ) {

        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                // 헤더에서 토큰 추출
                val accessToken = authHeader.substring(7)

                if (jwtTokenProvider.validateAccessToken(accessToken)) {
                    // Save authentication in SecurityContextHolder
                    val authentication = jwtTokenProvider.getAuthentication(accessToken)
                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        } catch (e: IncorrectClaimException) { // 잘못된 토큰일 경우
            SecurityContextHolder.clearContext();
            response.sendError(403);
        } catch (e: UsernameNotFoundException) { // 회원을 찾을 수 없을 경우
            SecurityContextHolder.clearContext();
            response.sendError(403);
        }

        filterChain.doFilter(request, response)
    }
}