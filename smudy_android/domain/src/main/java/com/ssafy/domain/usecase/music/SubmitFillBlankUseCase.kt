package com.ssafy.domain.usecase.music

import com.ssafy.domain.repository.StudyRepository
import javax.inject.Inject

class SubmitFillBlankUseCase @Inject constructor(
    private val studyRepository: StudyRepository
) {
    suspend operator fun invoke(songId: String, inputs: List<String>)
    = studyRepository.submitFillBlank(songId, inputs)
}