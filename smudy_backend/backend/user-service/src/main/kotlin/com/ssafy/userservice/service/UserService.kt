package com.ssafy.userservice.service

import com.ssafy.userservice.db.postgre.entity.User
import com.ssafy.userservice.db.postgre.repository.UserRepository
import com.ssafy.userservice.dto.request.SignUpRequest
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
        private val userRepository: UserRepository
) {
    fun signup(signUpRequest: SignUpRequest) : String{
        val user = userRepository.findByUserSnsId(signUpRequest.userSnsId)

        if (user.isPresent) {
            return ""
        }

        val saveUser = userRepository
                .save(
                    User(
                            userId = -1,
                            userExp = 0,
                            userName = signUpRequest.userSnsName,
                            userImage = signUpRequest.userImage,
                            userInternalId = UUID.randomUUID(),
                            userSnsId = signUpRequest.userSnsId,
                            createdAt = Date()
                    )
                )

        if (saveUser.userId == -1) {
            return ""
        }

        return saveUser.userInternalId.toString()
    }
}