package com.ssafy.studyservice.service

import com.ssafy.studyservice.exception.exception.JwtExpiredException
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.security.Key
import java.util.*
import java.util.stream.Collectors

@Service
class JwtService(
        @Value("\${jwt.secret}") secretKey: String
) {
    private val key: Key

    init {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        key = Keys.hmacShaKeyFor(keyBytes)
    }

    fun getUserInternalId(request: HttpServletRequest): String {
        return try {
            val accessToken = resolveToken(request)
            val claims = parseClaims(accessToken)
            val userInternalId = claims["internal_id"].toString()
            userInternalId
        } catch (e: ExpiredJwtException) {
            // 토큰이 만료된 경우
            throw JwtExpiredException("Expired refresh token.")
        } catch (e: UnsupportedJwtException) {
            // 지원되지 않는 JWT 형식인 경우
            throw UnsupportedJwtException("Unsupported JWT token.", e)
        } catch (e: MalformedJwtException) {
            // JWT 형식이 잘못된 경우
            throw MalformedJwtException("Invalid JWT token.", e)
        } catch (e: IllegalArgumentException) {
            // refreshToken 인자가 잘못된 경우 (null 또는 빈 문자열 등)
            throw IllegalArgumentException("JWT token compact of handler are invalid.", e)
        } catch (e: Exception) {
            throw JwtException("JWT Parse Error", e)
        }
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
        val roles = claims["role"] as String?  // 클레임에서 권한 정보 추출

        if (internalId == null || roles == null) {
            throw JwtException("토큰에 필요한 클레임 정보가 없습니다.")
        }

        val authorities = roles.map { SimpleGrantedAuthority(it.toString()) }  // 권한 정보를 GrantedAuthority 객체 리스트로 변환

        val principal = User(internalId, "", authorities)  // Spring Security의 User 객체 사용

        return UsernamePasswordAuthenticationToken(principal, null, authorities)  // Authentication 객체 생성
    }

}
