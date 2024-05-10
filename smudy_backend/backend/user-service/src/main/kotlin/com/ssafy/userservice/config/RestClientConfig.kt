package com.ssafy.userservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.FormHttpMessageConverter
import org.springframework.web.client.RestTemplate

@Configuration
class RestClientConfig {

    @Bean
    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate()
        restTemplate.messageConverters.add(FormHttpMessageConverter())  // FormHttpMessageConverter 추가
        return restTemplate
    }
}