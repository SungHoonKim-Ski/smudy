package com.ssafy.domain.repository

import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.Lyric
import com.ssafy.domain.model.SongWithBlank
import kotlinx.coroutines.flow.Flow

interface StudyRepository {
    suspend fun getDailyLyric(): Flow<ApiResult<Lyric>>
    suspend fun getSongWithBlank(songId: String): Flow<ApiResult<SongWithBlank>>
}