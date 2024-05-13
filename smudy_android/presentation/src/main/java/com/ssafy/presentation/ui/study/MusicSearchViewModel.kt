package com.ssafy.presentation.ui.study

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.usecase.music.AddStudyListUseCase
import com.ssafy.domain.usecase.music.GetMusicListUseCase
import com.ssafy.domain.usecase.music.SearchKeywordUseCase
import com.ssafy.presentation.model.Study
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicSearchViewModel @Inject constructor(
    private val searchKeywordUseCase: SearchKeywordUseCase,
    private val getMusicListUseCase: GetMusicListUseCase,
    private val addStudyListUseCase: AddStudyListUseCase
) : ViewModel() {

    private val _keywords = MutableStateFlow<List<String>>(emptyList())
    val keywords = _keywords.asStateFlow()

    private val _isAddSuccess = MutableStateFlow("")
    val isAddSuccess = _isAddSuccess.asStateFlow()

    private val _musicsToAddList = mutableSetOf<String>()
    fun searchKeyword(query: String) {
        viewModelScope.launch {
            searchKeywordUseCase(query).collect {
                when (it) {
                    is ApiResult.Success -> {
                        _keywords.emit(it.data)
                    }

                    is ApiResult.Failure -> {

                    }

                    is ApiResult.Loading -> {}
                }
            }
        }
    }

    fun getMusicList(query: String): Flow<PagingData<Study>> {
        return getMusicListUseCase(query).map { pagingData ->
            pagingData.map {
                Study(it.spotifyId, it.albumJacket, it.songName, it.songArtist, false)
            }
        }.cachedIn(viewModelScope)
    }

    fun addMusicList() {
        viewModelScope.launch {
            addStudyListUseCase(_musicsToAddList.toList()).collect {
                when (it) {
                    is ApiResult.Success -> {
                        if (it.data!=-1) {
                            _isAddSuccess.emit("중복된 곡을 제외한 ${it.data}곡을 추가하였습니다.")
                        } else {
                            _isAddSuccess.emit("모든 곡이 이미 존재합니다.")
                        }
                    }
                    is ApiResult.Loading -> {}
                    is ApiResult.Failure -> {}
                }
            }
        }
    }

    fun addMusic(musicId: String) {
        _musicsToAddList.add(musicId)
    }

    fun deleteMusic(musicId: String) {
        _musicsToAddList.remove(musicId)
    }

    fun deleteKeywords() {
        _keywords.value = emptyList()
    }
}