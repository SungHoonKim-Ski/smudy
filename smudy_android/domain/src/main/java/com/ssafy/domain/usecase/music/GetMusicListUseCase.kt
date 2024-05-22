package com.ssafy.domain.usecase.music

import com.ssafy.domain.repository.MusicRepository
import javax.inject.Inject

class GetMusicListUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    operator fun invoke(query: String) = musicRepository.getMusicPagingDate(query)
}