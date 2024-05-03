package com.ssafy.domain.usecase.music

import com.ssafy.domain.repository.MusicRepository
import javax.inject.Inject

class GetStudyListUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    operator fun invoke() = musicRepository.getStudyPagingData()
}