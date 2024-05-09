package com.ssafy.presentation.ui.study.pronounce

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.usecase.study.pronounce.GetPronounceProblemUseCase
import com.ssafy.domain.usecase.study.pronounce.GetTranslateLyric
import com.ssafy.presentation.model.PronounceProblem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PronounceProblemViewModel @Inject constructor(
    private val getPronounceProblemUseCase: GetPronounceProblemUseCase,
    private val getTranslateLyric: GetTranslateLyric
) : ViewModel() {
    private val _pronounceProblem =
        MutableStateFlow(PronounceProblem("", "", "", "", emptyList()))
    val pronounceProblem = _pronounceProblem.asStateFlow()
    private val _translatedLyric = MutableStateFlow<List<String>>(emptyList())
    val translateLyric = _translatedLyric.asStateFlow()

    fun getPronounceProblem(id: String) {
        viewModelScope.launch {
            getPronounceProblemUseCase(id).collect {
                when (it) {
                    is ApiResult.Success -> {
                        Log.e("TAG", "getPronounceProblem: ${it.data.songId}")
                        it.data.apply {
                            _pronounceProblem.emit(
                                PronounceProblem(
                                    songId,
                                    songArtist,
                                    songName,
                                    albumJacket,
                                    lyrics
                                )
                            )
                        }
                    }

                    is ApiResult.Failure -> {}
                    is ApiResult.Loading -> {}
                }
            }
        }
    }

    fun getTranslateLyric(lyricPosition: Int) {
        viewModelScope.launch {
            val lyric = _pronounceProblem.value.lyrics[lyricPosition]
            getTranslateLyric(lyric).collect {
                when (it) {
                    is ApiResult.Success -> {
                        _translatedLyric.emit(listOf(lyric, it.data))
                    }

                    is ApiResult.Failure -> {
                        _translatedLyric.emit(listOf(lyric, ""))
                    }
                    is ApiResult.Loading -> {}
                }
            }
        }
    }
}