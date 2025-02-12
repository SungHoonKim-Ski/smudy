package com.ssafy.authservice.common.config

import com.ssafy.authservice.common.jwt.*
import com.ssafy.authservice.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher



@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint
) {

    @Bean
    fun jwtSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http {
            csrf { disable() }
            httpBasic { disable() }
            formLogin { disable() }
            exceptionHandling {
                accessDeniedHandler = jwtAccessDeniedHandler
                authenticationEntryPoint = jwtAuthenticationEntryPoint
            }

            authorizeRequests {

                // 임시로 /api 요청은 모두 허용
                authorize(AntPathRequestMatcher("/api/auth/login", "POST"), permitAll)
                authorize(AntPathRequestMatcher("/api/auth/signup", "POST"), permitAll)
//                authorize(AntPathRequestMatcher("/**", "PUT"), permitAll)
//                authorize(AntPathRequestMatcher("/**", "DELETE"), permitAll)
                // 그외의 요청은 모두 차단
                authorize(anyRequest, authenticated)
            }

            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }

            addFilterBefore<UsernamePasswordAuthenticationFilter>(JwtAuthenticationFilter(jwtTokenProvider))
        }

        return http.build()
    }

    @Bean
    fun oAuthAuthenticationProvider(): AuthenticationProvider {
        val userDetailsService = UserDetailsServiceImpl(userRepository)
        return OAuthAuthenticationProvider(userDetailsService)
    }
}