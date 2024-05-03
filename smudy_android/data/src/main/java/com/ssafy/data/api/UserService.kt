package com.ssafy.data.api

import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.user.HistoryResponse
import com.ssafy.data.model.user.RecommendedSongsResponse
import com.ssafy.data.model.user.StreakResponse
import com.ssafy.data.model.user.UserInfoDto
import com.ssafy.data.model.user.WrongLyricDto
import com.ssafy.domain.model.user.Song
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("user/info")
    suspend fun getUserInfo(): Result<DefaultResponse<UserInfoDto>>

    @GET("user/streak")
    suspend fun getStreak(): Result<DefaultResponse<StreakResponse>>

    @GET("user/wrong")
    suspend fun getWrong(): Result<DefaultResponse<WrongLyricDto>>

    @GET("search/recommend")
    suspend fun getRecommendedSong(): Result<DefaultResponse<RecommendedSongsResponse>>

    @GET("user/history")
    suspend fun getHistory(
        @Query("time") date: Long
    ): Result<DefaultResponse<HistoryResponse>>

}