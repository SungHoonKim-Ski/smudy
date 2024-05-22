package com.ssafy.domain.usecase.study

import com.ssafy.domain.repository.ShuffleRepository
import javax.inject.Inject

class GetShuffleUseCase @Inject constructor(
    private val repository: ShuffleRepository
) {
    suspend operator fun invoke(songId: String) = repository.getShuffle(songId)
}