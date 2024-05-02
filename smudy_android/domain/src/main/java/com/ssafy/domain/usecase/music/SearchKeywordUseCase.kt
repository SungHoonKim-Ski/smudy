package com.ssafy.domain.usecase.music

import com.ssafy.domain.repository.MusicRepository
import javax.inject.Inject

class SearchKeywordUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(query: String) = musicRepository.searchKeyword(query)
}