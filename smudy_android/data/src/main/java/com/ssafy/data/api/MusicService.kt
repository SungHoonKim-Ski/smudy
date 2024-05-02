package com.ssafy.data.api

import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.music.AddStudyListRequest
import com.ssafy.data.model.music.AutoCreateResponse
import com.ssafy.data.model.music.StudyListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MusicService {

    @GET("user/studylist")
    suspend fun getStudyList(@Query("page") page: Int): Response<DefaultResponse<StudyListResponse>>

    @POST("user/studylist/add")
    suspend fun addStudyList(@Body studyList: AddStudyListRequest): Result<DefaultResponse<Int>>

    @GET("search/autocreate")
    suspend fun searchKeyWord(@Query("query") query: String): Result<DefaultResponse<AutoCreateResponse>>

    @GET("search")
    suspend fun getMusicList(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<DefaultResponse<StudyListResponse>>
}