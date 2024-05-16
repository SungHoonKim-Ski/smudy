package com.ssafy.presentation.ui.study

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.usecase.music.DeleteStudyListUseCase
import com.ssafy.domain.usecase.music.GetStudyListUseCase
import com.ssafy.presentation.model.Study
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class StudyViewModel @Inject constructor(
    private val getStudyListUseCase: GetStudyListUseCase,
    private val deleteStudyListUseCase: DeleteStudyListUseCase
) : ViewModel() {

    fun getStudyList(): Flow<PagingData<Study>> {
        return getStudyListUseCase().map { pagingData ->
            pagingData.map {
                Study(it.spotifyId, it.albumJacket, it.songName, it.songArtist, false)
            }
        }.cachedIn(viewModelScope)
    }

    fun deleteStudyList(songId: String) {
        viewModelScope.launch {
            deleteStudyListUseCase(songId).collect{
                when(it){
                    is ApiResult.Success->{
                        getStudyList()
                    }
                    is ApiResult.Failure->{}
                    is ApiResult.Loading->{}
                }
            }
        }
    }
}