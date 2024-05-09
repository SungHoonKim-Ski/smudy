package com.ssafy.data.api

import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.music.pronounce.PronounceResponse
import com.ssafy.data.model.music.pronounce.TranslatedLyricResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PronounceService {
    @GET("study/pronounce/{songId}")
    suspend fun getPronounceProblemInfo(@Path("songId") id:String): Result<DefaultResponse<PronounceResponse>>

    @GET("study/translate")
    suspend fun getTranslatedLyric(@Query("lyric") lyric:String):Result<DefaultResponse<TranslatedLyricResponse>>
}