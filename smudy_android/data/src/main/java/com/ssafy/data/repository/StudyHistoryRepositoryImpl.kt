package com.ssafy.data.repository

import com.ssafy.data.datasource.user.remote.history.StudyHistoryRemoteDataSource
import com.ssafy.data.mapper.toExpressGradedResult
import com.ssafy.data.model.music.express.ExpressAnswerRequest
import com.ssafy.domain.model.ApiError
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.ShuffleSubmitResult
import com.ssafy.domain.model.SubmitFillBlankData
import com.ssafy.domain.model.study.express.ExpressGradedResult
import com.ssafy.domain.repository.StudyHistoryRepository
import com.ssafy.util.NetworkException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class StudyHistoryRepositoryImpl @Inject constructor(
    private val studyHistoryRemoteDataSource: StudyHistoryRemoteDataSource
) : StudyHistoryRepository {
    override suspend fun getPickHistory(learnReportId: String) = buildFlow(
        studyHistoryRemoteDataSource.getPickHistory(learnReportId),
        ""
    ) as Flow<ApiResult<ShuffleSubmitResult>>

    override suspend fun getFillHistory(learnReportId: String) =
        buildFlow(
            studyHistoryRemoteDataSource.getFillHistory(learnReportId),
            ""
        ) as Flow<ApiResult<SubmitFillBlankData>>

    override suspend fun getExpressHistory(learnReportId: String): Flow<ApiResult<List<ExpressGradedResult>>> =
        flow {
            val response = studyHistoryRemoteDataSource.getExpressHistory(learnReportId)
            val data = response.getOrNull()
            if (data != null) {
                emit(ApiResult.Success(data.data!!.userExpresses.map { it.toExpressGradedResult() }))
            } else {
                val exception = response.exceptionOrNull() as NetworkException
                emit(ApiResult.Failure(ApiError(exception.code, exception.message)))
            }
        }.onStart { emit(ApiResult.Loading()) }
}