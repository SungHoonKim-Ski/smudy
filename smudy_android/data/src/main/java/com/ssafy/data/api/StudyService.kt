package com.ssafy.data.api

import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.user.DailyLyricDto
import retrofit2.http.GET

interface StudyService {
    @GET("study/daily")
    suspend fun getDailyLyric(): Result<DefaultResponse<DailyLyricDto>>
}