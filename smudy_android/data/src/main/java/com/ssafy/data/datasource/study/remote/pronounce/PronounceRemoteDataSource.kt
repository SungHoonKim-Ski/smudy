package com.ssafy.data.datasource.study.remote.pronounce

import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.music.pronounce.PronounceResponse
import com.ssafy.data.model.music.pronounce.TranslatedLyricResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PronounceRemoteDataSource {
    suspend fun getPronounceProblemInfo(id:String): Result<DefaultResponse<PronounceResponse>>
    suspend fun getTranslatedLyric(lyric:String):Result<DefaultResponse<TranslatedLyricResponse>>
    suspend fun gradePronounceProblem(userFile: MultipartBody.Part,ttsFile: MultipartBody.Part,json: RequestBody): Result<DefaultResponse<Boolean>>
}