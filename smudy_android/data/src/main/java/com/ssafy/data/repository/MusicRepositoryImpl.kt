package com.ssafy.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ssafy.data.api.MusicService
import com.ssafy.data.datasource.music.MusicPagingDataSource
import com.ssafy.domain.model.Study
import com.ssafy.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val musicService: MusicService
): MusicRepository {
    override fun getMusicPagingData(): Flow<PagingData<Study>> {
        return Pager(PagingConfig(pageSize = 10)) {
            MusicPagingDataSource(musicService)
        }.flow
    }
}