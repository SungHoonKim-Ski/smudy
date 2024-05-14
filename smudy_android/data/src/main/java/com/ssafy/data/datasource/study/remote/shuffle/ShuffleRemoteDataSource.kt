package com.ssafy.data.datasource.study.remote.shuffle

import com.ssafy.data.model.music.shuffle.ShuffleQuestionResponse

interface ShuffleRemoteDataSource {
    suspend fun getShuffleQuestion(songId: String): Result<ShuffleQuestionResponse>
}