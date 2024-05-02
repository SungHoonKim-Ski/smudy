package com.ssafy.data.datasource.music

import com.ssafy.data.api.MusicService
import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.music.KeywordResponse
import javax.inject.Inject

class MusicRemoteDataSourceImpl @Inject constructor(
    private val musicService: MusicService
):MusicRemoteDataSource {
    override suspend fun searchKeyword(query: String): Result<DefaultResponse<KeywordResponse>> {
        return musicService.searchKeyWord(query)
    }
}