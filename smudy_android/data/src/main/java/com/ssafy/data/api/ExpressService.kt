package com.ssafy.data.api

import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.music.express.ExpressAnswerRequest
import com.ssafy.data.model.music.express.ExpressGradedResultResponse
import com.ssafy.data.model.music.express.ExpressProblemResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ExpressService {
    @GET("study/express/{songId}")
    suspend fun getExpressProblem(@Path("songId") songId:String):Result<DefaultResponse<ExpressProblemResponse>>

    @POST("study/express/check")
    suspend fun checkExpressProblem(@Body expressAnswerRequest: ExpressAnswerRequest):Result<DefaultResponse<ExpressGradedResultResponse>>
}