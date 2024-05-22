package com.ssafy.studyservice.config

import com.ssafy.studyservice.service.JwtService
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
                authorize("/api/study/feign/problems/**", permitAll)
                authorize("/api/study/test", permitAll)

                authorize("/api/study/translate", authenticated)
                authorize("/api/study/express/check", authenticated)
                authorize("/api/study/daily", authenticated)
                authorize("/api/study/fill/**", authenticated)
                authorize("/api/study/pick/**", authenticated)
                authorize("/api/study/express/**", authenticated)
                authorize("/api/study/pronounce/**", authenticated)

//                authorize("/api/study/**/**", authenticated)
            }

            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }

            addFilterBefore<UsernamePasswordAuthenticationFilter>(JwtAuthenticationFilter(jwtService))
        }

        return http.build()
    }
}