package com.ssafy.authservice.common.jwt

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication

class OAuthAuthenticationProvider(
    private val userDetailsServiceImpl: UserDetailsServiceImpl
) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication): Authentication {

        val userSnsId = authentication.principal as String
        // UserDetailsService를 통해 사용자 정보 가져오기
        val userDetails = userDetailsServiceImpl.loadUserByUsername(userSnsId)

        return UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.authorities
        )
    }

    override fun supports(authentication: Class<*>): Boolean {
        return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}