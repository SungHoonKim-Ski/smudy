package com.ssafy.domain.usecase.study

import com.ssafy.domain.repository.ShuffleRepository
import javax.inject.Inject

class SubmitShuffleUseCase @Inject constructor(
    private val shuffleRepository: ShuffleRepository
) {
    suspend operator fun invoke(
        songId: String, problemBoxId: Int, userPicks: List<String>
    ) = shuffleRepository.submitShuffle(
        songId, problemBoxId, userPicks
    )
}