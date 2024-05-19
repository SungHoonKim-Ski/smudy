package com.ssafy.presentation.ui.study.express

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.study.express.ExpressResultDto
import com.ssafy.domain.model.study.express.GradedExpressResultDto
import com.ssafy.domain.usecase.study.express.CheckExpressProblemUseCase
import com.ssafy.domain.usecase.study.express.GetExpressProblemInfoUseCase
import com.ssafy.domain.usecase.study.express.SubmitExpressResultUseCase
import com.ssafy.presentation.model.Music
import com.ssafy.presentation.model.express.ExpressQuestion
import com.ssafy.presentation.model.express.ExpressResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpressViewModel @Inject constructor(
    private val getExpressProblemInfoUseCase: GetExpressProblemInfoUseCase,
    private val checkExpressProblemUseCase: CheckExpressProblemUseCase,
    private val submitExpressResultUseCase: SubmitExpressResultUseCase
) : ViewModel() {

    private lateinit var songId: String
    private var problemBoxId: Int = 0

    // 전체 문제 list
    private var expressProblems: MutableList<ExpressQuestion> = mutableListOf()
    private var expressResults: MutableList<ExpressResult> = mutableListOf()

    // 현재 풀이 문제 index flow
    private val _currentProblemIndex = MutableStateFlow(-1)
    val currentProblemIndex = _currentProblemIndex.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    // 채점 결과 저장 list
    // 앨범 정보 dto flow
    private val _album = MutableStateFlow(Music("", "", ""))
    val album = _album.asStateFlow()

    private val _navigationTrigger = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1
    )
    val navigationTrigger: SharedFlow<String> = _navigationTrigger.asSharedFlow()
    private suspend fun triggerNavigation(destination: String) {
        _navigationTrigger.emit(destination)  // 이벤트 발행
    }

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

    fun getCurrentExpressResult(): ExpressResult = expressResults.last()
    fun getAlbumInfo(): Music = _album.value
    fun getCurrentCount(): Int = currentProblemIndex.value

    fun getExpressProblem(): ArrayList<ExpressResult> = arrayListOf<ExpressResult>().apply {
        expressResults.map {
            add(it)
        }
    }

    fun isComplete(count: Int) = viewModelScope.launch {
        Log.e("TAG", "isComplete: $count ${expressProblems.size}")
        if (expressProblems.size == count) {
            val request = GradedExpressResultDto(
                problemBoxId,
                songId,
                expressResults.map {
                    ExpressResultDto(
                        it.suggestedSentenceEn,
                        it.userAnswerSentenceEn,
                        it.userAnswerSentenceKo,
                        it.score
                    )
                })
            submitExpressResultUseCase(request).collect {
                when (it) {
                    is ApiResult.Success -> {
                        triggerNavigation(NAVIGATE_TO_RESULT)
                    }

                    is ApiResult.Failure -> {}
                    is ApiResult.Loading -> {}
                }
            }
        } else {
            _currentProblemIndex.emit(currentProblemIndex.value + 1)
        }
    }

    fun checkExpressProblem(answer: String) {
        viewModelScope.launch {
            _isLoading.emit(true)
            checkExpressProblemUseCase(
                expressProblems[_currentProblemIndex.value].lyricSentenceEn,
                expressProblems[_currentProblemIndex.value].lyricSentenceKo,
                answer
            ).collect {
                when (it) {
                    is ApiResult.Success -> {
                        expressResults.add(
                            ExpressResult(
                                it.data.suggestLyricSentence,
                                it.data.lyricSentenceKo,
                                it.data.userLyricSentenceEn,
                                it.data.userLyricSentenceKo,
                                it.data.score
                            )
                        )
                        _isLoading.emit(false)
                        triggerNavigation(SHOW_DIALOG)
                    }

                    is ApiResult.Failure -> { _isLoading.emit(false) }
                    is ApiResult.Loading -> { _isLoading.emit(true) }
                }
            }
        }
    }

    companion object {
        private val SHOW_DIALOG = "show_dialog"
        private val NAVIGATE_TO_RESULT = "result_screen"
    }
}