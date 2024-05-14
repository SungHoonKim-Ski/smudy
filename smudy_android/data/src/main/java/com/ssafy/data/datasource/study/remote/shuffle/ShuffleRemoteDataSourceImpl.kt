package com.ssafy.data.datasource.study.remote.shuffle

import com.ssafy.data.api.ShuffleService
import com.ssafy.data.mapper.toNonDefault
import com.ssafy.data.model.music.shuffle.ShuffleQuestionResponse
import javax.inject.Inject

class ShuffleRemoteDataSourceImpl @Inject constructor(
    private val shuffleService: ShuffleService
) : ShuffleRemoteDataSource {
    override suspend fun getShuffleQuestion(songId: String): Result<ShuffleQuestionResponse> {
        return shuffleService.getShuffle(songId).toNonDefault()
    }
}