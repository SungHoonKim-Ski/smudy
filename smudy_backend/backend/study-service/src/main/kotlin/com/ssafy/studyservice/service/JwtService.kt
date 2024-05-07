package com.ssafy.studyservice.service

import com.ssafy.studyservice.exception.exception.JwtExpiredException
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
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
}
