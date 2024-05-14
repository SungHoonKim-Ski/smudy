package com.ssafy.presentation.ui.study.shuffle

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.ShuffleQuestion
import com.ssafy.domain.model.ShuffleQuestionProblem
import com.ssafy.domain.usecase.study.GetShuffleUseCase
import com.ssafy.presentation.model.ShuffleWord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ShuffleViewModel"

@HiltViewModel
class ShuffleViewModel @Inject constructor(
    private val getShuffleUseCase: GetShuffleUseCase
) : ViewModel() {

    private val _shuffleResult = MutableStateFlow<ApiResult<ShuffleQuestion>>(ApiResult.Loading())

    val shuffleResult = _shuffleResult.asStateFlow()

    private var _curIdx = -1
    val curIdx: Int get() = _curIdx

    fun setCurIdx(idx: Int) {
        _curIdx = idx
    }

    val koreanList = mutableListOf<String>()
    val shuffledList = mutableListOf<MutableList<ShuffleWord>>()
    val inputWords = mutableListOf<String>()

    val selectedList = mutableListOf<MutableList<ShuffleWord>>()

    private val blankList = List(20) { " ".repeat(10) }.map {
        ShuffleWord(it, -1)
    }


    fun getShuffle(id: String) {
        viewModelScope.launch {
            getShuffleUseCase(id).collect {
                when (it) {
                    is ApiResult.Success -> {
                        Log.d(TAG, "getShuffle: a")
                        koreanList.clear()
                        shuffledList.clear()
                        it.data.problems.forEach { pr ->
                            koreanList.add(pr.lyricSentenceKo)
                            shuffledList.add(
                                pr.lyricSentenceEn.split(" ").shuffled().mapIndexed { index, s ->
                                    ShuffleWord(s, index)
                                }.toMutableList()
                            )
                            selectedList.add(
                                blankList.toMutableList()
                            )
                            inputWords.add("")
                        }
                    }

                    is ApiResult.Failure -> {
                        Log.d(TAG, "getShuffle: fail ${it.apiError}")
                    }

                    is ApiResult.Loading -> {
                        Log.d(TAG, "getShuffle: loading")
                    }
                }
                _shuffleResult.emit(it)
            }
        }
    }

}