package com.ssafy.data.repository

import com.ssafy.data.datasource.study.remote.shuffle.ShuffleRemoteDataSource
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.ShuffleQuestion
import com.ssafy.domain.repository.ShuffleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ShuffleRepositoryImpl @Inject constructor(
    private val shuffleRemoteDataSource: ShuffleRemoteDataSource
): ShuffleRepository {
    override suspend fun getShuffle(songId: String) = buildFlow(
        shuffleRemoteDataSource.getShuffleQuestion(songId), ""
    )  as Flow<ApiResult<ShuffleQuestion>>
}