package com.ssafy.domain.repository

import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.study.pronounce.PronounceProblemInfo
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PronounceRepository {
    suspend fun getPronounceProblemInfo(id: String): Flow<ApiResult<PronounceProblemInfo>>
    suspend fun getTranslatedLyric(lyric: String): Flow<ApiResult<String>>
    suspend fun gradePronounceProblem(userFile: File, ttsFile: File, lyric: String, lyricKo: String): Flow<ApiResult<Boolean>>
}