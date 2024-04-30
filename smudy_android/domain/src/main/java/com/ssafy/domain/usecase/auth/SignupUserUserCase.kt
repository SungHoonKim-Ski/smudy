package com.ssafy.domain.usecase.auth

import com.ssafy.domain.repository.AuthRepository
import javax.inject.Inject

class SignupUserUserCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(id: String, name: String, image: String) = authRepository.signup(id, name, image)
}