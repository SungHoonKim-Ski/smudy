package com.ssafy.domain.repository

import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.InputAnswer
import com.ssafy.domain.model.Lyric
import com.ssafy.domain.model.SongWithBlank
import com.ssafy.domain.model.SubmitFillBlankData
import kotlinx.coroutines.flow.Flow

interface StudyRepository {
    suspend fun getDailyLyric(): Flow<ApiResult<Lyric>>
    suspend fun getSongWithBlank(songId: String): Flow<ApiResult<SongWithBlank>>

    suspend fun submitFillBlank(
        songId: String,
        inputs: List<String>
    ): Flow<ApiResult<SubmitFillBlankData>>
}