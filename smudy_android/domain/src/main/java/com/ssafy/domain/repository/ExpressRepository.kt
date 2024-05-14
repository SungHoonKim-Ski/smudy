package com.ssafy.domain.repository

import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.study.express.ExpressGradedResult
import com.ssafy.domain.model.study.express.ExpressProblem
import kotlinx.coroutines.flow.Flow

interface ExpressRepository {
    suspend fun getExpressProblem(songId: String): Flow<ApiResult<ExpressProblem>>
    suspend fun checkExpressProblem(
        lyricSentenceEn: String,
        lyricSentenceKo: String,
        userSentence: String,
    ): Flow<ApiResult<ExpressGradedResult>>
}