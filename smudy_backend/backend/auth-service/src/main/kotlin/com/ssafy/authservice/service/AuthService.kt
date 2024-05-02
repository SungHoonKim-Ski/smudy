package com.ssafy.authservice.service

import com.ssafy.authservice.common.jwt.JwtTokenProvider
import com.ssafy.authservice.dto.request.LoginRequest
import com.ssafy.authservice.dto.response.TokenResponse
import org.springframework.http.HttpHeaders.SERVER
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors


@Service
class AuthService(
        private val jwtTokenProvider: JwtTokenProvider,
        private val authenticationManagerBuilder: AuthenticationManagerBuilder,
        private val userService: UserService,
        private val redisService: RedisService
) {

    // 로그인
    @Transactional
    fun login(request: LoginRequest): TokenResponse {

        val userSnsId = request.userSnsId
        val userInternalId = userService.extractInternalId(userSnsId)   // 못찾았을 때, Exception 처리 추가해야함

        val authenticationToken = UsernamePasswordAuthenticationToken(
            userInternalId, ""
        )

        val authentication: Authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken)

        SecurityContextHolder.getContext().authentication = authentication

        return generateToken(authentication.name, getAuthorities(authentication))
    }

    // 자동로그인 (액세스 토큰 유효성만 확인)
    fun autoLogin(requestAccessTokenInHeader: String): Boolean {

        val accessToken = resolveToken(requestAccessTokenInHeader)
        return jwtTokenProvider.validateAccessTokenOnlyExpired(accessToken)
    }

    // 토큰 재발급 (액세스 토큰이 유효기간 내에 있을 경우)
    @Transactional
    fun reissue(
            requestAccessTokenInHeader: String,
            requestRefreshToken: String
    ): TokenResponse? {

        val accessToken = resolveToken(requestAccessTokenInHeader)
        val authentication = jwtTokenProvider.getAuthentication(accessToken)
        val internalId = getPrincipal(accessToken)
        val refreshTokenInRedis = redisService.getValues("refresh-token:$internalId")
            ?: return null // Redis에 저장된 Refresh-token이 없을 경우 재로그인 요청

        // Refresh-token 유효성 검증 및 Redis 토큰 비교
        if (!jwtTokenProvider.validateRefreshToken(requestRefreshToken) || refreshTokenInRedis != requestRefreshToken) {
            // 리프래시 토큰이 탈취당했을 수 있음
            redisService.deleteValues("refresh-token:$internalId")
            return null // 재로그인 요청
        }

        SecurityContextHolder.getContext().authentication = authentication
        val authorities = getAuthorities(authentication)

        // Redis 업데이트 및 새로운 토큰 재발급
        redisService.deleteValues("refresh-token:$internalId")
        val tokenResponse = jwtTokenProvider.createToken(internalId, authorities)
        saveRefreshToken(internalId, tokenResponse.refreshToken)

        return tokenResponse
    }

    // 토큰 발급
    @Transactional
    fun generateToken(internalId: String, authorities: String): TokenResponse {
        // 리프래시 토큰이 존재하는 경우
        if (redisService.getValues("refresh-token:$internalId") != null) {
            redisService.deleteValues("refresh-token:$internalId") // 삭제
        }

        // 액세스 토큰, 리프래시 토큰 생성
        val tokenResponse = jwtTokenProvider.createToken(internalId, authorities)
        saveRefreshToken(internalId, tokenResponse.refreshToken)
        return tokenResponse
    }

    // 리프래시 토큰을 Redis에 저장
    @Transactional
    fun saveRefreshToken(principal: String, refreshToken: String) {
        redisService.setValuesWithTimeout(
                "refresh-token:$principal",     // key
                refreshToken,                       // value
                jwtTokenProvider.getTokenExpirationTime(refreshToken)) // timeout
    }

    // 권한 이름 가져오기
    fun getAuthorities(authentication: Authentication): String {
        return authentication.authorities.stream()
                .map { obj: GrantedAuthority -> obj.authority }
                .collect(Collectors.joining(","))
    }

    // 앞에 있는 "Bearer " 제거 후 토큰 추출
    fun resolveToken(accessTokenInHeader: String?): String? {
        return if (accessTokenInHeader != null && accessTokenInHeader.startsWith("Bearer ")) {
            accessTokenInHeader.substring(7)
        } else null
    }

    // principal (InternalId) 가져오기
    fun getPrincipal(accessToken: String?): String {
        return jwtTokenProvider.getAuthentication(accessToken!!).name
    }
}