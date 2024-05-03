package com.ssafy.authservice.service

import com.ssafy.authservice.common.exception.CustomException
import com.ssafy.authservice.common.exception.ExceptionType
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
    private val authService: AuthService,
    private val userRepository: UserRepository
) {

    @Transactional
    fun registerUser(userSnsId: String, userSnsName: String, userImage: String): TokenResponse {

        // Check if the user already exists
        val existingUser = userRepository.findByUserSnsId(userSnsId)

        if (existingUser != null) {
            // 이미 가입된 회원인 경우
            throw CustomException(ExceptionType.DUPLICATE_USER_EXCEPTION)
        }

        val user = User(
            userInternalId = UUID.randomUUID(), // Generate unique ID
            userSnsId = userSnsId,
            userName = userSnsName,
            userImage = userImage,
            userRole = Role.USER // Default role
        )

        userRepository.save(user) // Save new user

        return authService.generateToken(user.userInternalId.toString(), user.userRole.key)
    }
}