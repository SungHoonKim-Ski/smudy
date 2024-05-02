package com.ssafy.data.datasource.study.remote

import com.ssafy.data.model.user.DailyLyricDto

interface StudyRemoteDataSource {
    suspend fun getDailyLyric(): Result<DailyLyricDto>
}