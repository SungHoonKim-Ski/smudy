package com.ssafy.authservice.service

import io.jsonwebtoken.JwtException
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.TimeUnit




@Service
class RedisService(
        private val redisTemplate: RedisTemplate<String, String>
) {

    @Transactional
    fun setValues(key: String?, value: String?) {
        redisTemplate.opsForValue()[key!!] = value!!
    }

    // Redis에 유효시간과 함께 저장
    @Transactional
    fun setValuesWithTimeout(key: String?, value: String?, timeout: Long) {
        redisTemplate.opsForValue()[key!!, value!!, timeout] = TimeUnit.MILLISECONDS
    }

    fun getValues(key: String): String {
        return redisTemplate.opsForValue()[key]
                ?: throw JwtException("${key}:JWT REDIS 조회 도중 에러")
    }

    fun deleteValues(key: String?) {
        redisTemplate.delete(key!!)
    }
}