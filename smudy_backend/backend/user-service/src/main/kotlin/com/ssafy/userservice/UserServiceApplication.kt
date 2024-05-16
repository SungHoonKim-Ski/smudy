package com.ssafy.userservice

import com.ssafy.userservice.exception.error.FeignErrorDecoder
import feign.Logger
import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import java.util.*

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
class UserServiceApplication

fun main(args: Array<String>) {
	runApplication<UserServiceApplication>(*args)
}

@Bean
fun feignLoggerLever(): Logger.Level {
	return Logger.Level.FULL
}

@PostConstruct
fun init() {
	TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
}


//@Bean
//fun getFeignErrorDecoder(): FeignErrorDecoder {
//	return FeignErrorDecoder()
//}
