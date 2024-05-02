package com.ssafy.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ssafy.data.api.MusicService
import com.ssafy.data.datasource.music.MusicPagingDataSource
import com.ssafy.domain.model.ApiError
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.Study
import com.ssafy.domain.repository.MusicRepository
import com.ssafy.util.NetworkException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val musicService: MusicService
) : MusicRepository {
    override fun getMusicPagingData(): Flow<PagingData<Study>> {
        return Pager(PagingConfig(pageSize = 10)) {
            MusicPagingDataSource(musicService)
        }.flow
    }

    override suspend fun searchKeyword(query: String): Flow<ApiResult<List<String>>> =
        flow {
            val response = musicService.searchKeyWord(query)
            val data = response.getOrNull()
            if (data != null) {
                emit(
                    ApiResult.Success(data.data!!.songNames.map {
                        it.songName
                    })
                )
            } else {
                val exception = response.exceptionOrNull() as NetworkException
                emit(ApiResult.Failure(ApiError(exception.code, exception.message)))
            }
        }.onStart { emit(ApiResult.Loading()) }
}