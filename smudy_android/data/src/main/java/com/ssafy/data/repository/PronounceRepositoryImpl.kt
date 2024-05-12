package com.ssafy.data.repository

import androidx.datastore.preferences.protobuf.Api
import com.ssafy.data.datasource.study.remote.pronounce.PronounceRemoteDataSource
import com.ssafy.data.mapper.toPronounceProblemInfo
import com.ssafy.domain.model.ApiError
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.study.pronounce.PronounceProblemInfo
import com.ssafy.domain.repository.PronounceRepository
import com.ssafy.util.NetworkException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class PronounceRepositoryImpl @Inject constructor(
    private val pronounceRemoteDataSource: PronounceRemoteDataSource
) : PronounceRepository {
    override suspend fun getPronounceProblemInfo(id: String): Flow<ApiResult<PronounceProblemInfo>> =
        flow {
            val response = pronounceRemoteDataSource.getPronounceProblemInfo(id)
            val data = response.getOrNull()
            if (data != null) {
                emit(ApiResult.Success(data.data!!.toPronounceProblemInfo()))
            } else {
                val exception = response.exceptionOrNull() as NetworkException
                emit(ApiResult.Failure(ApiError(exception.code, exception.message)))
            }
        }.onStart { emit(ApiResult.Loading()) }

    override suspend fun getTranslatedLyric(lyric: String): Flow<ApiResult<String>> =
        flow {
            val response = pronounceRemoteDataSource.getTranslatedLyric(lyric)
            val data = response.getOrNull()
            if (data != null) {
                emit(ApiResult.Success(data.data!!.lyricKo))
            } else {
                val exception = response.exceptionOrNull() as NetworkException
                emit(ApiResult.Failure(ApiError(exception.code, exception.message)))
            }
        }.onStart { emit(ApiResult.Loading()) }
}