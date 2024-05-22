package com.ssafy.data.api

import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.music.shuffle.ShuffleQuestionResponse
import com.ssafy.data.model.music.shuffle.ShuffleSubmitRequest
import com.ssafy.data.model.music.shuffle.ShuffleSubmitResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ShuffleService {

    @GET("study/pick/{songId}")
    suspend fun getShuffle(@Path("songId") songId: String) : Result<DefaultResponse<ShuffleQuestionResponse>>

    @POST("user/pick/submit")
    suspend fun submitShuffle(@Body shuffleRequest: ShuffleSubmitRequest) : Result<DefaultResponse<ShuffleSubmitResponse>>

}