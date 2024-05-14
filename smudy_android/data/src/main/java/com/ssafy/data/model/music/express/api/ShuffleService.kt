package com.ssafy.data.model.music.express.api

import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.music.shuffle.ShuffleQuestionResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ShuffleService {

    @GET("study/pick/{songId}")
    suspend fun getShuffle(@Path("songId") songId: String) : Result<DefaultResponse<ShuffleQuestionResponse>>

}