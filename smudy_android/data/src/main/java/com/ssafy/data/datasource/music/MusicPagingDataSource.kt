package com.ssafy.data.datasource.music

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssafy.data.api.MusicService
import com.ssafy.data.mapper.toStudy
import com.ssafy.domain.model.Study
import com.ssafy.util.NetworkException

class MusicPagingDataSource(
    private val musicService: MusicService
) : PagingSource<Int, Study>() {
    override fun getRefreshKey(state: PagingState<Int, Study>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.let {
                it.prevKey?.plus(1) ?: it.nextKey?.minus(1)
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Study> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = musicService.getStudyList(page)
            val musicList =
                if (response.isSuccessful) response.body()?.data?.toStudy().orEmpty() else emptyList()
            if (musicList.isNotEmpty()) {
                LoadResult.Page(
                    data = musicList,
                    prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1),
                    nextKey = if (musicList.isEmpty()) null else page.plus(1)
                )
            } else {
                LoadResult.Error(NetworkException(response.code().toString(), "오류입니다."))
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}