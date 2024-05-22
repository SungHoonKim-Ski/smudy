package com.ssafy.data.datasource.study.remote.shuffle

import com.ssafy.data.api.ShuffleService
import com.ssafy.data.mapper.toNonDefault
import com.ssafy.data.model.music.shuffle.ShuffleQuestionResponse
import com.ssafy.data.model.music.shuffle.ShuffleSubmitRequest
import com.ssafy.data.model.music.shuffle.ShuffleSubmitResponse
import javax.inject.Inject

class ShuffleRemoteDataSourceImpl @Inject constructor(
    private val shuffleService: ShuffleService
) : ShuffleRemoteDataSource {
    override suspend fun getShuffleQuestion(songId: String): Result<ShuffleQuestionResponse> {
        return shuffleService.getShuffle(songId).toNonDefault()
    }

    override suspend fun submitShuffleAnswer(request: ShuffleSubmitRequest): Result<ShuffleSubmitResponse> {
        return shuffleService.submitShuffle(request).toNonDefault()
    }
}