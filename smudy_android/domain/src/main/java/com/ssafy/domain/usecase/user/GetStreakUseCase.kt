package com.ssafy.domain.usecase.user

import com.ssafy.domain.repository.UserRepository
import javax.inject.Inject

class GetStreakUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.getStreak()

}