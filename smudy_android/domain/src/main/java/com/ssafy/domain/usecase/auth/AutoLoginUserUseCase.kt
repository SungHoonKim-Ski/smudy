package com.ssafy.domain.usecase.auth

import com.ssafy.domain.repository.AuthRepository
import javax.inject.Inject

class AutoLoginUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() = authRepository.autoLogin()
}