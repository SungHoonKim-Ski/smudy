package com.ssafy.data.datasource.study.remote

import com.ssafy.data.api.StudyService
import com.ssafy.data.mapper.toNonDefault
import com.ssafy.data.model.user.DailyLyricDto
import javax.inject.Inject

class StudyRemoteDataSourceImpl @Inject constructor(
    private val studyService: StudyService
) : StudyRemoteDataSource{
    override suspend fun getDailyLyric(): Result<DailyLyricDto> {
        return studyService.getDailyLyric().toNonDefault()
    }
}