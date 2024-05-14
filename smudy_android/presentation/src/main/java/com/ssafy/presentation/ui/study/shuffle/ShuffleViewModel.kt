package com.ssafy.presentation.ui.study.shuffle

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.ShuffleQuestion
import com.ssafy.domain.model.ShuffleQuestionProblem
import com.ssafy.domain.model.ShuffleSubmitResult
import com.ssafy.domain.usecase.study.GetShuffleUseCase
import com.ssafy.domain.usecase.study.SubmitShuffleUseCase
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
    private val getShuffleUseCase: GetShuffleUseCase,
    private val submitShuffleUseCase: SubmitShuffleUseCase
) : ViewModel() {

    private val _shuffleResult = MutableStateFlow<ApiResult<ShuffleQuestion>>(ApiResult.Loading())
    val shuffleResult = _shuffleResult.asStateFlow()

    private val _shuffleSubmitResult = MutableStateFlow<ApiResult<ShuffleSubmitResult>>(ApiResult.Loading())
    val shuffleSubmitResult = _shuffleSubmitResult.asStateFlow()

    private var boxId = -1

    private var _curIdx = MutableLiveData(-1)
    val curIdx: LiveData<Int> get() = _curIdx

    fun setCurIdx(idx: Int) {
        _curIdx.value = idx
    }

    val koreanList = mutableListOf<String>()
    val shuffledList = mutableListOf<MutableList<ShuffleWord>>()
    val inputWords = mutableListOf<MutableList<String>>()

    val selectedList = mutableListOf<MutableList<ShuffleWord>>()

    private val blankList = List(20) { " ".repeat(10) }.map {
        ShuffleWord(it, -1)
    }


    fun getShuffle(id: String) {
        viewModelScope.launch {
            getShuffleUseCase(id).collect {
                when (it) {
                    is ApiResult.Success -> {
                        boxId = it.data.problemBoxId
                        koreanList.clear()
                        shuffledList.clear()
                        it.data.problems.forEach { pr ->
                            koreanList.add(pr.lyricSentenceKo)
                            val sList = pr.lyricSentenceEn.split(" ").shuffled().mapIndexed { index, s ->
                                ShuffleWord(s, index, true)
                            }.toMutableList()
                            shuffledList.add(sList)
                            selectedList.add(
                                blankList.toMutableList()
                            )
                            inputWords.add(MutableList(sList.size){""})
                        }
                    }
                    is ApiResult.Failure -> { Log.d(TAG, "getShuffle: fail ${it.apiError}") }

                    is ApiResult.Loading -> { Log.d(TAG, "getShuffle: loading") }
                }
                _shuffleResult.emit(it)
            }
        }
    }

    fun submitShuffle(id: String){
        viewModelScope.launch {
            submitShuffleUseCase(id, boxId, inputWords.map{
                it.joinToString(" ")
            }).collect{
                _shuffleSubmitResult.emit(it)
            }
        }
    }

}