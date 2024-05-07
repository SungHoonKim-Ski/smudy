package com.ssafy.data.datasource.study.remote.pronounce

import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.music.pronounce.PronounceResponse
import com.ssafy.data.model.music.pronounce.TranslatedLyricResponse

interface PronounceRemoteDataSource {
    suspend fun getPronounceProblemInfo(id:String): Result<DefaultResponse<PronounceResponse>>
    suspend fun getTranslatedLyric(lyric:String):Result<DefaultResponse<TranslatedLyricResponse>>
}