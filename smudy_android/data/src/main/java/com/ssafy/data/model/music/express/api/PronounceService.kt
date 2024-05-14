package com.ssafy.data.model.music.express.api

import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.music.pronounce.GradedPronounceResponse
import com.ssafy.data.model.music.pronounce.PronounceResponse
import com.ssafy.data.model.music.pronounce.TranslatedLyricResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface PronounceService {
    @GET("study/pronounce/{songId}")
    suspend fun getPronounceProblemInfo(@Path("songId") id: String): Result<DefaultResponse<PronounceResponse>>

    @GET("study/translate")
    suspend fun getTranslatedLyric(@Query("lyric") lyric: String): Result<DefaultResponse<TranslatedLyricResponse>>

    @Multipart
    @POST("user/pronounce/submit")
    suspend fun gradePronounceProblem(
        @Part userFile: MultipartBody.Part,
        @Part ttsFile: MultipartBody.Part,
        @Part("json") json: RequestBody
    ): Result<DefaultResponse<GradedPronounceResponse>>
}