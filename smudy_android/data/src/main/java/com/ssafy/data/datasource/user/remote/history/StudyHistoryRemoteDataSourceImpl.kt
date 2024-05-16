package com.ssafy.data.datasource.user.remote.history

import com.ssafy.data.api.HistoryService
import com.ssafy.data.mapper.toNonDefault
import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.music.express.ExpressHistoryResponse
import com.ssafy.data.model.music.fill.SubmitFillBlankResponse
import com.ssafy.data.model.music.shuffle.ShuffleSubmitResponse
import javax.inject.Inject

class StudyHistoryRemoteDataSourceImpl @Inject constructor(
    private val historyService: HistoryService
): StudyHistoryRemoteDataSource {
    override suspend fun getPickHistory(learnReportId: String): Result<ShuffleSubmitResponse>
        = historyService.getPickHistory(learnReportId).toNonDefault()

    override suspend fun getFillHistory(learnReportId: String): Result<SubmitFillBlankResponse> =
        historyService.getFillHistory(learnReportId).toNonDefault()

    override suspend fun getExpressHistory(learnReportId: String): Result<DefaultResponse<ExpressHistoryResponse>> =
        historyService.getExpressHistory(learnReportId)
}