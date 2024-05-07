package com.ssafy.authservice.common.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.ssafy.authservice.common.exception.ErrorCode
import com.ssafy.authservice.common.exception.ErrorResponse
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.IncorrectClaimException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

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
            filterChain.doFilter(request, response)
        } catch (e: ExpiredJwtException) {
            SecurityContextHolder.clearContext();
            setErrorResponse(response, ErrorCode.JWT_TOKEN_EXPIRED)
        } catch (e: IncorrectClaimException) { // 잘못된 토큰일 경우
            SecurityContextHolder.clearContext();
            setErrorResponse(response, ErrorCode.JWT_ERROR)
        } catch (e: UsernameNotFoundException) { // 회원을 찾을 수 없을 경우
            SecurityContextHolder.clearContext();
            setErrorResponse(response, ErrorCode.JWT_ERROR)
        } catch (e: Exception) {
            SecurityContextHolder.clearContext();
            setErrorResponse(response, ErrorCode.JWT_ERROR)
        }
    }

    private fun setErrorResponse(
            response: HttpServletResponse,
            errorCode: ErrorCode
    ) {
        val objectMapper = ObjectMapper()
        response.status = errorCode.status.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        val errorResponse = ErrorResponse(errorCode, errorCode.message)
        try {
            response.writer.write(objectMapper.writeValueAsString(errorResponse))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}