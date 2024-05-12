package com.ssafy.data.api

import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.music.SubmitFillBlankRequest
import com.ssafy.data.model.music.SongWithBlankDto
import com.ssafy.data.model.music.SubmitFillBlankResponse
import com.ssafy.data.model.user.DailyLyricDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StudyService {
    @GET("study/daily")
    suspend fun getDailyLyric(): Result<DefaultResponse<DailyLyricDto>>

    @GET("study/fill/{songId}")
    suspend fun getSongWithBlank(@Path("songId") songId: String): Result<DefaultResponse<SongWithBlankDto>>

    @POST("user/fill/submit")
    suspend fun submitFillBlankAnswer(
        @Body request: SubmitFillBlankRequest
    ): Result<DefaultResponse<SubmitFillBlankResponse>>

}