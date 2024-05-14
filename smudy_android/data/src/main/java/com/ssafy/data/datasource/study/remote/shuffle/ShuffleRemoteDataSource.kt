package com.ssafy.data.datasource.study.remote.shuffle

import com.ssafy.data.model.music.shuffle.ShuffleQuestionResponse
import com.ssafy.data.model.music.shuffle.ShuffleSubmitRequest
import com.ssafy.data.model.music.shuffle.ShuffleSubmitResponse

interface ShuffleRemoteDataSource {
    suspend fun getShuffleQuestion(songId: String): Result<ShuffleQuestionResponse>

    suspend fun submitShuffleAnswer(request: ShuffleSubmitRequest): Result<ShuffleSubmitResponse>
}