package com.ssafy.authservice.common.jwt

import com.ssafy.authservice.dto.response.TokenResponse
import com.ssafy.authservice.service.RedisService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.security.Key
import java.util.*

@Component
@Transactional
class JwtTokenProvider(
    private val userDetailsService : UserDetailsServiceImpl,
    private val redisService: RedisService,
    @Value("\${jwt.secret-key}") private val secretKey: String
) : InitializingBean {

    private val accessTokenExpireTime = 1000 * 60 * 30              // 30분
    private val refreshTokenExpireTime = 1000 * 60 * 60 * 24 * 7    // 7일
    private lateinit var signingKey: Key

    // BASE64 디코딩 후 서명 키로 사용
    override fun afterPropertiesSet() {
        val secretKeyBytes = Decoders.BASE64.decode(secretKey)
        signingKey = Keys.hmacShaKeyFor(secretKeyBytes)
    }

    // 토큰 생성
    @Transactional
    fun createToken(internalId: UUID, authorities: String): TokenResponse {
        val now = System.currentTimeMillis()

        val accessToken = Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setHeaderParam("alg", "HS512")
            .setExpiration(Date(now + accessTokenExpireTime))
            .setSubject("access-token")
            .claim("url", true)
            .claim("internal_id", internalId.toString())
            .claim("role", authorities)
            .signWith(signingKey, SignatureAlgorithm.HS512)
            .compact()

        val refreshToken = Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setHeaderParam("alg", "HS512")
            .setExpiration(Date(now + refreshTokenExpireTime))
            .setSubject("refresh-token")
            .signWith(signingKey, SignatureAlgorithm.HS512)
            .compact()

        return TokenResponse(
            grantType = "Bearer",
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    // 토큰 정보 추출
    fun getAuthentication(token: String): Authentication {
        val internalId = getClaims(token)["internal_id"].toString()
        val userDetails = userDetailsService.loadUserByUsername(internalId)
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities())
    }

    fun getClaims(token: String): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: ExpiredJwtException) {
            e.claims
        }
    }

    fun getTokenExpirationTime(token: String): Long {
        return getClaims(token).expiration.time
    }

    // 리프래시 토큰 검증
    fun validateRefreshToken(refreshToken: String): Boolean {
        try {
            if (redisService.getValues(refreshToken) == "delete") {
                return false
            }
            getClaims(refreshToken)
            return true
        } catch (e: ExpiredJwtException) {
            e.claims
        }
        return false
    }

    // 액세스 토큰 검증
    fun validateAccessToken(accessToken: String): Boolean {
        return try {
            if (redisService.getValues(accessToken) == "logout") {
                return false
            }

            getClaims(accessToken)
            true
        } catch (e: ExpiredJwtException) {
            true // 만료된 토큰은 특정 경우에 유효하다고 간주할 수 있음
        } catch (e: Exception) {
            false
        }
    }

    fun validateAccessTokenOnlyExpired(accessToken: String): Boolean {
        return try {
            getClaims(accessToken)
                .expiration
                .before(Date())
        } catch (e: ExpiredJwtException) {
            true
        } catch (e: Exception) {
            false
        }
    }
}
