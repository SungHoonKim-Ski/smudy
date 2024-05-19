package com.ssafy.data.datasource.music

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssafy.data.api.MusicService
import com.ssafy.data.mapper.toStudy
import com.ssafy.data.model.music.MusicListResponse
import com.ssafy.data.model.music.StudyListResponse
import com.ssafy.domain.model.Study
import com.ssafy.util.NetworkException

class MusicPagingDataSource(
    private val musicService: MusicService,
    private val isSearch:Boolean = false,
    private val query:String = ""
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
            val response = if (isSearch) musicService.getMusicList(query,page) else musicService.getStudyList(page)
            val musicList =
                if (response.isSuccessful) {
                    if (response.body()?.data is MusicListResponse){
                        (response.body()?.data as MusicListResponse).toStudy().distinctBy { Pair(it.songName,it.songArtist) }
                    } else {
                        (response.body()?.data as StudyListResponse).toStudy().distinctBy { Pair(it.songName,it.songArtist) }
                    }
                } else emptyList()
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
        private const val STARTING_PAGE_INDEX = 0
    }
}