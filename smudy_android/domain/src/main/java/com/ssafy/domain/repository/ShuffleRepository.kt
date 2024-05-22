package com.ssafy.domain.repository

import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.ShuffleQuestion
import com.ssafy.domain.model.ShuffleSubmitResult
import kotlinx.coroutines.flow.Flow

interface ShuffleRepository {

    suspend fun getShuffle(songId: String): Flow<ApiResult<ShuffleQuestion>>

    suspend fun submitShuffle(songId: String, problemBoxId: Int, userPicks: List<String>): Flow<ApiResult<ShuffleSubmitResult>>


}