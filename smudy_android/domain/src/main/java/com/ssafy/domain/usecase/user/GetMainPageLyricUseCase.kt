package com.ssafy.domain.usecase.user

import com.ssafy.domain.repository.StudyRepository
import com.ssafy.domain.repository.UserRepository
import javax.inject.Inject

class GetMainPageLyricUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val studyRepository: StudyRepository
) {
    suspend operator fun invoke(isDaily: Boolean = true) =
        if(isDaily) {
            studyRepository.getDailyLyric()
        } else {
            userRepository.getWrongLyric()
        }
}