package com.ssafy.data.datasource.study.remote.pronounce

import com.ssafy.data.api.PronounceService
import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.music.pronounce.PronounceResponse
import com.ssafy.data.model.music.pronounce.TranslatedLyricResponse
import javax.inject.Inject

class PronounceRemoteDataSourceImpl @Inject constructor(
    private val pronounceService: PronounceService
): PronounceRemoteDataSource {
    override suspend fun getPronounceProblemInfo(id: String): Result<DefaultResponse<PronounceResponse>> {
        return pronounceService.getPronounceProblemInfo(id)
    }

    override suspend fun getTranslatedLyric(lyric: String): Result<DefaultResponse<TranslatedLyricResponse>> {
        return pronounceService.getTranslatedLyric(lyric)
    }

}