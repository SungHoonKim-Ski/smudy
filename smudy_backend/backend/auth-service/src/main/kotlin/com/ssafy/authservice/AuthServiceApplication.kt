package com.ssafy.authservice

import com.ssafy.authservice.config.FeignErrorDecoder
import feign.Logger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
class AuthServiceApplication

fun main(args: Array<String>) {
	runApplication<AuthServiceApplication>(*args)
}

@Bean
fun feignLoggerLever(): Logger.Level? {
	return Logger.Level.FULL
}

@Bean
fun getFeignErrorDecoder(): FeignErrorDecoder {
	return FeignErrorDecoder()
}
