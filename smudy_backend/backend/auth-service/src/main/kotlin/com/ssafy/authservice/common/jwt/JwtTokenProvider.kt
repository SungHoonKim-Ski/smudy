package com.ssafy.authservice.common.jwt

import com.ssafy.authservice.dto.response.TokenResponse
import com.ssafy.authservice.service.RedisService
import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
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

    private val logger = KotlinLogging.logger { }

    private val accessTokenExpireTime = 1000 * 30            // 테스트용 1초
//    private val accessTokenExpireTime = 1000 * 60 * 30              // 30분
    private val refreshTokenExpireTime = 1000 * 60 * 60 * 24 * 7    // 7일
    private lateinit var signingKey: Key

    // BASE64 디코딩 후 서명 키로 사용
    override fun afterPropertiesSet() {
        val secretKeyBytes = Decoders.BASE64.decode(secretKey)
        signingKey = Keys.hmacShaKeyFor(secretKeyBytes)
    }

    // 토큰 생성
    @Transactional
    fun createToken(internalId: String, authorities: String): TokenResponse {
        val now = System.currentTimeMillis()

        val accessToken = Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setHeaderParam("alg", "HS512")
            .setExpiration(Date(now + accessTokenExpireTime))
            .setSubject("access-token")
            .claim("url", true)
            .claim("internal_id", internalId)
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
    fun getAuthentication(accessToken: String): Authentication {

        val internalId = getClaims(accessToken)["internal_id"].toString()
        val principal: UserDetails = userDetailsService.loadUserByUsername(internalId)
        return UsernamePasswordAuthenticationToken(
            principal,
            "",
            principal.authorities
        )
    }

    fun getAuthenticationWithClaims(claims: Claims): Authentication {

//        if(claims["auth"] == null) {
//            throw JwtException("권한 정보가 없는 토큰입니다.")
//        }

        val internalId = claims["internal_id"].toString()
        val principal: UserDetails = userDetailsService.loadUserByUsername(internalId)
        return UsernamePasswordAuthenticationToken(
                principal,
                "",
                principal.authorities
        )
    }

    fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .body
    }

    fun getTokenExpirationTime(token: String): Long {
        return getClaims(token).expiration.time
    }

    // 리프래시 토큰 검증
    fun validateRefreshToken(refreshToken: String): Boolean {
        try {
            if (redisService.getValues(refreshToken) == "delete") {
                logger.error { "User has been deleted." }
                return false
            }
            getClaims(refreshToken)

            return true
        } catch (e: SignatureException) {
            logger.error(e) { "Invalid JWT signature." }
        } catch (e: MalformedJwtException) {
            logger.error(e) { "Invalid JWT token." }
        } catch (e: ExpiredJwtException) {
            logger.error(e) { "Expired JWT token." }
        } catch (e: UnsupportedJwtException) {
            logger.error(e) { "Unsupported JWT token." }
        } catch (e: IllegalArgumentException) {
            logger.error(e) { "JWT claims string is empty." }
        }

        return false
    }

    // 액세스 토큰 검증 (Filter에서 사용)
    fun validateAccessToken(accessToken: String): Boolean {

//        if (redisService.getValues(accessToken) == "logout") {
//            return false
//        }

        getClaims(accessToken)
        return true
    }

    // 액세스 토큰 유효기간 검증 (재발급 시에 사용, 만료된 경우 true)
    fun validateAccessTokenOnlyExpired(accessToken: String?): Boolean {
        return try {
            getClaims(accessToken!!)
                .expiration
                .before(Date())
        } catch (e: ExpiredJwtException) {
            true
        } catch (e: SignatureException) {
            false
        } catch (e: Exception) {
            false
        }
    }
}
