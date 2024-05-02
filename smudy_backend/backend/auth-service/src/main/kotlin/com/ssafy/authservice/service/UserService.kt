package com.ssafy.authservice.service

import com.ssafy.authservice.common.jwt.JwtTokenProvider
import com.ssafy.authservice.dto.response.TokenResponse
import com.ssafy.authservice.entity.Role
import com.ssafy.authservice.entity.User
import com.ssafy.authservice.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository
) {

    @Transactional
    fun registerUser(userSnsId: String, userSnsName: String, userImage: String): TokenResponse {

        // Check if the user already exists
        val existingUser = userRepository.findByUserSnsId(userSnsId)

        if (existingUser != null) {
            throw IllegalArgumentException("이미 가입된 회원입니다: $userSnsId")
        }

        val user = User(
            userInternalId = UUID.randomUUID(), // Generate unique ID
            userSnsId = userSnsId,
            userName = userSnsName,
            userImage = userImage,
            userRole = Role.USER // Default role
        )

        userRepository.save(user) // Save new user

        return jwtTokenProvider.createToken(user.userInternalId.toString(), user.userRole.key)
    }

    @Transactional
    fun extractInternalId(userSnsId: String): String {
        // Request 로 userSnsId 를 받지만, Authentication 과정에선 userInternalId 필요
        val user: User? = userRepository.findByUserSnsId(userSnsId)
        return user?.userInternalId.toString() ?: throw IllegalArgumentException("No user found with the given userSnsId")
    }
}