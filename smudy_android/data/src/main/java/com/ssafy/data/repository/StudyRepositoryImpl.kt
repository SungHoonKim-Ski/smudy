package com.ssafy.data.repository

import com.ssafy.data.datasource.study.remote.StudyRemoteDataSource
import com.ssafy.domain.model.ApiError
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.Lyric
import com.ssafy.domain.model.SongWithBlank
import com.ssafy.domain.repository.StudyRepository
import com.ssafy.util.NetworkException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class StudyRepositoryImpl @Inject constructor(
    private val studyRemoteDataSource: StudyRemoteDataSource
) : StudyRepository {

    override suspend fun getDailyLyric() = buildFlow(
        studyRemoteDataSource.getDailyLyric(), ""
    ) as Flow<ApiResult<Lyric>>

    override suspend fun getSongWithBlank(songId: String) = buildFlow(
        studyRemoteDataSource.getSongWithBlank(songId), ""
    ) as Flow<ApiResult<SongWithBlank>>

}