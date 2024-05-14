package com.ssafy.domain.repository

import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.ShuffleQuestion
import kotlinx.coroutines.flow.Flow

interface ShuffleRepository {

    suspend fun getShuffle(songId: String): Flow<ApiResult<ShuffleQuestion>>

}