package com.ssafy.presentation.ui.study.express

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.usecase.study.express.CheckExpressProblemUseCase
import com.ssafy.domain.usecase.study.express.GetExpressProblemInfoUseCase
import com.ssafy.presentation.model.Music
import com.ssafy.presentation.model.express.ExpressQuestion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpressViewModel @Inject constructor(
    private val getExpressProblemInfoUseCase: GetExpressProblemInfoUseCase,
    private val checkExpressProblemUseCase: CheckExpressProblemUseCase
) : ViewModel() {

    private lateinit var songId: String
    private var problemBoxId: Int = 0

    // 전체 문제 list
    private var expressProblems: MutableList<ExpressQuestion> = mutableListOf()

    // 현재 풀이 문제 index flow
    private val _currentProblemIndex = MutableStateFlow(-1)
    val currentProblemIndex = _currentProblemIndex.asStateFlow()

    // 채점 결과 저장 list
    // 앨범 정보 dto flow
    private val _album = MutableStateFlow(Music("", "", ""))
    val album = _album.asStateFlow()
    fun setSongId(id: String) {
        songId = id
        viewModelScope.launch {
            getExpressProblemInfoUseCase(songId).collect {
                when (it) {
                    is ApiResult.Success -> {
                        it.data.let { expressProblem ->
                            expressProblems.apply {
                                expressProblem.problems.map {
                                    add(
                                        ExpressQuestion(
                                            it.lyricSentenceEn,
                                            it.lyricSentenceKo
                                        )
                                    )
                                }
                            }
                            _album.emit(
                                Music(
                                    expressProblem.songName,
                                    expressProblem.songArtist,
                                    expressProblem.albumJacket
                                )
                            )
                            _currentProblemIndex.emit(0)
                            problemBoxId = expressProblem.problemBoxId
                        }
                    }

                    is ApiResult.Failure -> {}
                    is ApiResult.Loading -> {}
                }
            }
        }
    }

    fun getCurrentExpressProblem(idx: Int): String =
        if (expressProblems.isEmpty()) "" else expressProblems[idx].lyricSentenceKo

    fun checkExpressProblem() {
        viewModelScope.launch {
            checkExpressProblemUseCase(
                expressProblems[_currentProblemIndex.value].lyricSentenceEn,
                expressProblems[_currentProblemIndex.value].lyricSentenceKo,
                "I'm in love"
            ).collect {
                when(it) {
                    is ApiResult.Success -> {
                        Log.e("TAG", "checkExpressProblem: ${it.data}")
                    }

                    is ApiResult.Failure -> {}
                    is ApiResult.Loading -> {}
                }
            }
            _currentProblemIndex.emit(currentProblemIndex.value + 1)
        }
    }
}