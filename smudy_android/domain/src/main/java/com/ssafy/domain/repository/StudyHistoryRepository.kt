package com.ssafy.domain.repository

import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.ShuffleSubmitResult
import com.ssafy.domain.model.SubmitFillBlankData
import com.ssafy.domain.model.study.express.ExpressGradedResult
import kotlinx.coroutines.flow.Flow

interface StudyHistoryRepository {
    suspend fun getPickHistory(learnReportId: String): Flow<ApiResult<ShuffleSubmitResult>>
    suspend fun getFillHistory(learnReportId: String): Flow<ApiResult<SubmitFillBlankData>>
    suspend fun getExpressHistory(learnReportId: String): Flow<ApiResult<List<ExpressGradedResult>>>
}