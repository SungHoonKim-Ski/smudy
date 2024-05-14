package com.ssafy.data.datasource.study.remote

import com.ssafy.data.model.music.fill.SongWithBlankDto
import com.ssafy.data.model.music.fill.SubmitFillBlankResponse
import com.ssafy.data.model.user.DailyLyricDto
import com.ssafy.domain.model.InputAnswer

interface StudyRemoteDataSource {
    suspend fun getDailyLyric(): Result<DailyLyricDto>
    suspend fun getSongWithBlank(songId: String): Result<SongWithBlankDto>

    suspend fun submitFillBlank(songId: String, inputs: List<InputAnswer>): Result<SubmitFillBlankResponse>
}