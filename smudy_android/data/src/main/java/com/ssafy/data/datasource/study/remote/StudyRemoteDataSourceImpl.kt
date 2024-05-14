package com.ssafy.data.datasource.study.remote

import com.ssafy.data.api.StudyService
import com.ssafy.data.mapper.toNonDefault
import com.ssafy.data.model.music.fill.SongWithBlankDto
import com.ssafy.data.model.music.fill.SubmitFillBlankRequest
import com.ssafy.data.model.music.fill.SubmitFillBlankResponse
import com.ssafy.data.model.user.DailyLyricDto
import com.ssafy.domain.model.InputAnswer
import javax.inject.Inject

class StudyRemoteDataSourceImpl @Inject constructor(
    private val studyService: StudyService
) : StudyRemoteDataSource{
    override suspend fun getDailyLyric(): Result<DailyLyricDto> {
        return studyService.getDailyLyric().toNonDefault()
    }

    override suspend fun getSongWithBlank(songId: String): Result<SongWithBlankDto> {
        return studyService.getSongWithBlank(songId).toNonDefault()
    }

    override suspend fun submitFillBlank(
        songId: String,
        inputs: List<InputAnswer>
    ): Result<SubmitFillBlankResponse> {
        return studyService.submitFillBlankAnswer(
            SubmitFillBlankRequest(songId, inputs)
        ).toNonDefault()
    }
}