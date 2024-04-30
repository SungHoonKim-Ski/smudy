package com.ssafy.authservice.common.jwt

import com.ssafy.authservice.entity.Role
import com.ssafy.authservice.entity.User
import com.ssafy.authservice.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(internalId: String): UserDetailsImpl {
        val findUser = userRepository.findByUserInternalId(UUID.fromString(internalId))
            ?: throw UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다: $internalId")

        return UserDetailsImpl(findUser)
    }
}
