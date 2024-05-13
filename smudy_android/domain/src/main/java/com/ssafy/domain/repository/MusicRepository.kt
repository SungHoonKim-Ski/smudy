package com.ssafy.domain.repository

import androidx.paging.PagingData
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.Study
import kotlinx.coroutines.flow.Flow

interface MusicRepository {
    fun getStudyPagingData(): Flow<PagingData<Study>>
    suspend fun searchKeyword(query: String): Flow<ApiResult<List<String>>>
    fun getMusicPagingDate(query: String): Flow<PagingData<Study>>

    suspend fun addStudyList(musicList:List<String>): Flow<ApiResult<Int>>
}