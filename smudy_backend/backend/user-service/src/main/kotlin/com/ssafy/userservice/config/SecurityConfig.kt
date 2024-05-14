package com.ssafy.userservice.config

import com.ssafy.userservice.service.JwtService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
        private val jwtService: JwtService
) {
    @Bean
    fun jwtSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http {
            csrf { disable() }
            httpBasic { disable() }
            formLogin { disable() }

            authorizeRequests {
                authorize("/api/user/info", authenticated)
                authorize("/api/user/streak", authenticated)
                authorize("/api/user/wrong", authenticated)
                authorize("/api/user/studylist/**", authenticated)
                authorize("/api/user/history/**", authenticated)
                authorize("/api/user/recommend", authenticated)

                authorize("/api/user/feign/**", permitAll)
                authorize("/api/user/test", permitAll)
                authorize("/api/user/mongo", permitAll)

                authorize("/v3/api-docs/**", permitAll)
                authorize("/swagger-ui/**",  permitAll)
                authorize(anyRequest, authenticated)
            }

            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }

            addFilterBefore<UsernamePasswordAuthenticationFilter>(JwtAuthenticationFilter(jwtService))
        }

        return http.build()
    }
}