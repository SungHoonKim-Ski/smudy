package com.ssafy.data.datasource.user.remote.history

import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.music.express.ExpressHistoryResponse
import com.ssafy.data.model.music.fill.SubmitFillBlankResponse
import com.ssafy.data.model.music.shuffle.ShuffleSubmitResponse

interface StudyHistoryRemoteDataSource {
    suspend fun getPickHistory(learnReportId: String): Result<ShuffleSubmitResponse>
    suspend fun getFillHistory(learnReportId: String): Result<SubmitFillBlankResponse>
    suspend fun getExpressHistory(learnReportId: String):Result<DefaultResponse<ExpressHistoryResponse>>
}