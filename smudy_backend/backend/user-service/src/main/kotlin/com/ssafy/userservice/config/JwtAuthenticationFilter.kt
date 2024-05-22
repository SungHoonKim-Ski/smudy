package com.ssafy.userservice.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.ssafy.userservice.exception.error.CommonErrorCode
import com.ssafy.userservice.exception.error.ErrorCode
import com.ssafy.userservice.exception.error.ErrorResponse
import com.ssafy.userservice.service.JwtService
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
        private val jwtService: JwtService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    ) {

        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                // 헤더에서 토큰 추출
                val accessToken = authHeader.substring(7)
                jwtService.validateToken(accessToken)
                val authentication = jwtService.getAuthentication(accessToken) // 사용자 인증 정보 가져오기
                SecurityContextHolder.getContext().authentication = authentication
            }
            filterChain.doFilter(request, response)
        } catch (e: ExpiredJwtException) {
            SecurityContextHolder.clearContext();
            setErrorResponse(response, CommonErrorCode.JWT_TOKEN_EXPIRED)
        } catch (e: IncorrectClaimException) { // 잘못된 토큰일 경우
            SecurityContextHolder.clearContext();
            e.printStackTrace()
            setErrorResponse(response, CommonErrorCode.JWT_ERROR)
        } catch (e: UsernameNotFoundException) { // 회원을 찾을 수 없을 경우
            SecurityContextHolder.clearContext();
            e.printStackTrace()
            setErrorResponse(response, CommonErrorCode.JWT_ERROR)
        } catch (e: Exception) {
            SecurityContextHolder.clearContext();
            e.printStackTrace()
            setErrorResponse(response, e)
        }
    }

    private fun setErrorResponse(
            response: HttpServletResponse,
            errorCode: ErrorCode
    ) {
        val objectMapper = ObjectMapper()
        response.status = errorCode.getHttpStatus().value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        val errorResponse = ErrorResponse(errorCode, errorCode.getMessage())
        try {
            response.writer.write(objectMapper.writeValueAsString(errorResponse))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun setErrorResponse(
            response: HttpServletResponse,
            e: Exception
    ) {
        val objectMapper = ObjectMapper()
        val error = CommonErrorCode.INTERNAL_SERVER_ERROR
        response.status = error.getHttpStatus().value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        val errorResponse = ErrorResponse(error, e.message ?: "INTERNAL_SERVER_ERROR")
        try {
            response.writer.write(objectMapper.writeValueAsString(errorResponse))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}