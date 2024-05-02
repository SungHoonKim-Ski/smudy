package com.ssafy.domain.repository

import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.Lyric
import kotlinx.coroutines.flow.Flow

interface StudyRepository {
    suspend fun getDailyLyric(): Flow<ApiResult<Lyric>>
}