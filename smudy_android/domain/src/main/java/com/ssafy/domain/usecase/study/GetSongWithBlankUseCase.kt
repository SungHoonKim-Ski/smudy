package com.ssafy.domain.usecase.study

import com.ssafy.domain.repository.StudyRepository
import javax.inject.Inject

class GetSongWithBlankUseCase @Inject constructor(
    private val studyRepository: StudyRepository
) {
    suspend operator fun invoke(songId: String) = studyRepository.getSongWithBlank(songId)
}