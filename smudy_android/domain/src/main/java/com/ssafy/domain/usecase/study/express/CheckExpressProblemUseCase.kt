package com.ssafy.domain.usecase.study.express

import com.ssafy.domain.repository.ExpressRepository
import javax.inject.Inject

class CheckExpressProblemUseCase @Inject constructor(
    private val expressRepository: ExpressRepository
) {
    suspend operator fun invoke(
        lyricSentenceEn: String,
        lyricSentenceKo: String,
        userSentence: String,
    ) = expressRepository.checkExpressProblem(lyricSentenceEn, lyricSentenceKo, userSentence)
}