package com.ssafy.data.repository

import com.squareup.moshi.Moshi
import com.ssafy.data.datasource.study.remote.pronounce.PronounceRemoteDataSource
import com.ssafy.data.mapper.toGradePronounce
import com.ssafy.data.mapper.toPronounceProblemInfo
import com.ssafy.data.model.music.pronounce.PronounceLyricRequest
import com.ssafy.domain.model.ApiError
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.study.pronounce.GradedPronounce
import com.ssafy.domain.model.study.pronounce.PronounceProblemInfo
import com.ssafy.domain.repository.PronounceRepository
import com.ssafy.util.NetworkException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class PronounceRepositoryImpl @Inject constructor(
    private val pronounceRemoteDataSource: PronounceRemoteDataSource,
    private val moshi: Moshi
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

    override suspend fun gradePronounceProblem(
        userFile: File,
        ttsFile: File,
        lyric: String,
        lyricKo: String,
        id:String
    ): Flow<ApiResult<GradedPronounce>> = flow {
        val recorderRequestBody = userFile.asRequestBody("audio/mp4".toMediaTypeOrNull())
        val ttsRequestBody = ttsFile.asRequestBody("audio/wav".toMediaTypeOrNull())

        val recorderPart =
            MultipartBody.Part.createFormData("userFile", userFile.name, recorderRequestBody)
        val ttsPart = MultipartBody.Part.createFormData("ttsFile", ttsFile.name, ttsRequestBody)
        val pronounceLyricRequest = PronounceLyricRequest(lyric, lyricKo,id)
        val jsonStr = moshi.adapter(PronounceLyricRequest::class.java).toJson(pronounceLyricRequest)
        val requestBody = jsonStr.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        val response = pronounceRemoteDataSource.gradePronounceProblem(recorderPart, ttsPart, requestBody)
        val data = response.getOrNull()
        if (data != null) {
            emit(ApiResult.Success(data.data!!.toGradePronounce()))
        } else {
            val exception = response.exceptionOrNull() as NetworkException
            emit(ApiResult.Failure(ApiError(exception.code, exception.message)))
        }
    }.onStart { emit(ApiResult.Loading()) }
}