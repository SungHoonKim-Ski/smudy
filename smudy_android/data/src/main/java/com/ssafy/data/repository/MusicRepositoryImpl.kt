package com.ssafy.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ssafy.data.api.MusicService
import com.ssafy.data.datasource.music.MusicPagingDataSource
import com.ssafy.data.datasource.music.MusicRemoteDataSource
import com.ssafy.data.model.music.AddStudyListRequest
import com.ssafy.data.model.music.SongIdResponse
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
    private val musicService: MusicService,
    private val musicRemoteDataSource: MusicRemoteDataSource
) : MusicRepository {
    override fun getStudyPagingData(): Flow<PagingData<Study>> {
        return Pager(PagingConfig(pageSize = 10)) {
            MusicPagingDataSource(musicService)
        }.flow
    }

    override suspend fun searchKeyword(query: String): Flow<ApiResult<List<String>>> =
        flow {
            val response = musicRemoteDataSource.searchKeyword(query)
            val data = response.getOrNull()
            if (data != null) {
                emit(
                    ApiResult.Success(data.data!!.songNames.toSet().toList().map { it.songName })
                )
            } else {
                val exception = response.exceptionOrNull()
                if (exception is NetworkException) {
                    emit(ApiResult.Failure(ApiError(exception.code, exception.message)))
                } else {
                    emit(
                        ApiResult.Failure(
                            ApiError(
                                "204",
                                (exception as NullPointerException).message!!
                            )
                        )
                    )
                }
            }
        }.onStart { emit(ApiResult.Loading()) }

    override fun getMusicPagingDate(query: String): Flow<PagingData<Study>> {
        return Pager(PagingConfig(pageSize = 10)) {
            MusicPagingDataSource(musicService, true, query)
        }.flow
    }

    override suspend fun addStudyList(musicList: List<String>): Flow<ApiResult<Int>> =
        flow {
            val request = AddStudyListRequest(musicList.map { SongIdResponse(it) })
            val response = musicRemoteDataSource.addStudyList(request)
            val data = response.getOrNull()
            if (data != null) {
                emit(ApiResult.Success(data.data!!.saveCount))
            } else {
                val exception = response.exceptionOrNull() as NetworkException
                if (exception.code == "409") {
                    emit(ApiResult.Success(-1))
                } else {
                    emit(ApiResult.Failure(ApiError(exception.code, exception.message)))
                }
            }
        }.onStart { emit(ApiResult.Loading()) }

    override suspend fun deleteStudyList(songId: String): Flow<ApiResult<Boolean>> =
        flow<ApiResult<Boolean>> {
            val response = musicRemoteDataSource.deleteStudyList(songId)
            val data = response.getOrNull()
            if (data != null) {
                emit(ApiResult.Success(true))
            } else {
                val exception = response.exceptionOrNull() as NetworkException
                emit(ApiResult.Failure(ApiError(exception.code, exception.message)))
            }
        }.onStart { emit(ApiResult.Loading()) }

}