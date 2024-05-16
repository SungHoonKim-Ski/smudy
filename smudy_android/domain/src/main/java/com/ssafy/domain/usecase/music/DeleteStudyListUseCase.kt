package com.ssafy.domain.usecase.music

import com.ssafy.domain.repository.MusicRepository
import javax.inject.Inject

class DeleteStudyListUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(songId: String) = musicRepository.deleteStudyList(songId)
}