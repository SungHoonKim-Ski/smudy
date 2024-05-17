package com.ssafy.data.api

import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.music.express.ExpressHistoryResponse
import com.ssafy.data.model.music.fill.SubmitFillBlankResponse
import com.ssafy.data.model.music.pronounce.GradedPronounceResponse
import com.ssafy.data.model.music.shuffle.ShuffleSubmitResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HistoryService {
    @GET("user/history/pick/{learnReportId}")
    suspend fun getPickHistory(@Path("learnReportId") learnReportId: String): Result<DefaultResponse<ShuffleSubmitResponse>>

    @GET("user/history/fill/{learnReportId}")
    suspend fun getFillHistory(@Path("learnReportId") learnReportId: String): Result<DefaultResponse<SubmitFillBlankResponse>>

    @GET("user/history/express/{learnReportId}")
    suspend fun getExpressHistory(@Path("learnReportId")learnReportId: String): Result<DefaultResponse<ExpressHistoryResponse>>

    @GET("user/history/pronounce/{learnReportId}")
    suspend fun getPronounceHistory(@Path("learnReportId")learnReportId: String): Result<DefaultResponse<GradedPronounceResponse>>
}