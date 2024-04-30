package com.ssafy.domain.repository

import androidx.paging.PagingData
import com.ssafy.domain.model.Study
import kotlinx.coroutines.flow.Flow

interface MusicRepository {
    fun getMusicPagingData(): Flow<PagingData<Study>>
}