package com.ssafy.domain.usecase.study.pronounce

import com.ssafy.domain.repository.PronounceRepository
import javax.inject.Inject

class GetTranslateLyricUseCase @Inject constructor(
    private val pronounceRepository: PronounceRepository
) {
    suspend operator fun invoke(lyric: String) = pronounceRepository.getTranslatedLyric(lyric)
}