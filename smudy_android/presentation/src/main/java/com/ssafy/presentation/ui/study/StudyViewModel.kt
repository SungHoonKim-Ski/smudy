package com.ssafy.presentation.ui.study

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.ssafy.domain.usecase.music.GetMusicListUseCase
import com.ssafy.presentation.model.Study
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

@HiltViewModel
class StudyViewModel @Inject constructor(
    private val getMusicListUseCase: GetMusicListUseCase
) : ViewModel() {

    fun getMusicList(): Flow<PagingData<Study>> {
        return getMusicListUseCase().map { pagingData ->
            pagingData.map {
                Study(it.spotifyId, it.albumJacket, it.songName, it.songArtist, false)
            }
        }.cachedIn(viewModelScope)
    }
}