package com.ssafy.domain.usecase.study.pronounce

import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.study.pronounce.PronounceProblemInfo
import com.ssafy.domain.repository.PronounceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPronounceProblemUseCase @Inject constructor(
    private val pronounceRepository: PronounceRepository
) {
    suspend operator fun invoke(id: String): Flow<ApiResult<PronounceProblemInfo>> =
        pronounceRepository.getPronounceProblemInfo(id)
}