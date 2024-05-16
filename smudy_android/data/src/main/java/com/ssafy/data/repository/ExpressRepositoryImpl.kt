package com.ssafy.data.repository

import com.ssafy.data.datasource.study.remote.express.ExpressRemoteDataSource
import com.ssafy.data.mapper.toExpressGradedResult
import com.ssafy.data.mapper.toExpressProblem
import com.ssafy.data.mapper.toExpressResultRequest
import com.ssafy.data.model.music.express.ExpressAnswerRequest
import com.ssafy.domain.model.ApiError
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.study.express.ExpressGradedResult
import com.ssafy.domain.model.study.express.ExpressProblem
import com.ssafy.domain.model.study.express.GradedExpressResultDto
import com.ssafy.domain.repository.ExpressRepository
import com.ssafy.util.NetworkException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class ExpressRepositoryImpl @Inject constructor(
    private val expressRemoteDataSource: ExpressRemoteDataSource
) : ExpressRepository {
    override suspend fun getExpressProblem(songId: String): Flow<ApiResult<ExpressProblem>> =
        flow {
            val response = expressRemoteDataSource.getExpressProblem(songId)
            val data = response.getOrNull()
            if (data != null) {
                emit(ApiResult.Success(data.data!!.toExpressProblem()))
            } else {
                val exception = response.exceptionOrNull() as NetworkException
                emit(ApiResult.Failure(ApiError(exception.code, exception.message)))
            }
        }.onStart { emit(ApiResult.Loading()) }

    override suspend fun checkExpressProblem(
        lyricSentenceEn: String,
        lyricSentenceKo: String,
        userSentence: String,
    ): Flow<ApiResult<ExpressGradedResult>> =
        flow {
            val response = expressRemoteDataSource.checkExpressProblem(
                ExpressAnswerRequest(
                    lyricSentenceEn,
                    lyricSentenceKo,
                    userSentence
                )
            )
            val data = response.getOrNull()
            if (data != null) {
                emit(ApiResult.Success(data.data!!.toExpressGradedResult()))
            } else {
                val exception = response.exceptionOrNull() as NetworkException
                emit(ApiResult.Failure(ApiError(exception.code, exception.message)))
            }
        }.onStart { emit(ApiResult.Loading()) }

    override suspend fun submitExpressProblem(expressResultDto: GradedExpressResultDto): Flow<ApiResult<Boolean>> =
        flow {
            val requestDto = expressResultDto.toExpressResultRequest()
            val response = expressRemoteDataSource.submitExpressProblem(requestDto)
            val data = response.getOrNull()
            if (data != null) {
                emit(ApiResult.Success(true))
            } else {
                val exception = response.exceptionOrNull() as NetworkException
                emit(ApiResult.Failure(ApiError(exception.code, exception.message)))
            }
        }.onStart { emit(ApiResult.Loading()) }
}