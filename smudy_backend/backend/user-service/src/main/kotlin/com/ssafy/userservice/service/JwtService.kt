package com.ssafy.userservice.service

import com.ssafy.userservice.exception.exception.JwtExpiredException
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.security.Key
import java.util.*

@Service
class JwtService(
        @Value("\${jwt.secret}") secretKey: String
) {
    private val key: Key

    init {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        key = Keys.hmacShaKeyFor(keyBytes)
    }

    fun getUserInternalId(): String {

        val authentication = SecurityContextHolder.getContext().authentication
        // ContextHolder의 authentication에서 userInternalId를 반환
        if(authentication != null && authentication.isAuthenticated) {
            val principal = authentication.principal
            if (principal is User) {
                return principal.username
            }
        }
        
        throw SecurityException("No authenticated user found.")
    }

    private fun resolveToken(request: HttpServletRequest): String {
        val accessToken = request.getHeader("Authorization")
        if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer")) {
            return accessToken.substring(7)
        }
        throw JwtException("JWT Resolve Failed")
    }

    private fun parseClaims(accessToken: String): Claims {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .body
    }

    fun validateToken(token: String): Boolean {
        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
        return true
    }

    fun getAuthentication(accessToken: String): Authentication {
        val claims = parseClaims(accessToken)  // JWT에서 클레임을 추출하는 메서드 필요

        val internalId = claims["internal_id"] as String?  // 클레임에서 internal_id 추출
        val role = claims["role"] as String?  // 클레임에서 권한 정보 추출

        if (internalId == null || role == null) {
            throw JwtException("토큰에 필요한 클레임 정보가 없습니다.")
        }

        val authorities = listOf(SimpleGrantedAuthority(role)) // 권한 정보를 GrantedAuthority 객체 리스트로 변환

        val principal = User(internalId, "", authorities)  // Spring Security의 User 객체 사용
        // Authentication 객체 생성
        return UsernamePasswordAuthenticationToken(principal, null, principal.authorities)
    }

}
