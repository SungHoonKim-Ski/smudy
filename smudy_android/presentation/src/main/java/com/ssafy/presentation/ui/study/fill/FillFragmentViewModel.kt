package com.ssafy.presentation.ui.study.fill

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.LyricBlank
import com.ssafy.domain.model.SongWithBlank
import com.ssafy.domain.usecase.study.GetSongWithBlankUseCase
import com.ssafy.presentation.base.toMilliSecond
import com.ssafy.presentation.model.BlankQuestion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FillFragmentViewModel @Inject constructor(
    private val getSongWithBlankUseCase: GetSongWithBlankUseCase
): ViewModel() {
    private lateinit var timer : Job

    private val _songResult = MutableStateFlow<ApiResult<SongWithBlank>>(ApiResult.Loading())
    val songResult = _songResult.asStateFlow()

    val timeLineIndexMap = mutableMapOf<Long, Int>()
    private val _blankQuestionList = mutableListOf<BlankQuestion>()
    val blankQuestionList: List<BlankQuestion>
        get() = _blankQuestionList

    fun setQuestionInput(input: String, idx: Int){
        _blankQuestionList[idx] = _blankQuestionList[idx].copy(
            inputAnswer = input
        )}

    fun getQuestionInput(idx: Int) = _blankQuestionList[idx].inputAnswer

    val tmpList = mutableListOf<LyricBlank>()

    private var endTime = -1L

    private val _curTime = MutableLiveData(0L)
    val curTime: LiveData<Long>
        get() = _curTime

    fun timerStart(){
        if(::timer.isInitialized) timer.cancel()

        timer = viewModelScope.launch {
            while(_curTime.value!!<=endTime+1){
                _curTime.value = _curTime.value!!.plus(100)
                delay(100L)
            }
        }
    }

    fun timerStop(){
        if(::timer.isInitialized) timer.cancel()
    }

    fun setCurTime(time: Long){
        _curTime.postValue(time)
    }

    fun getSongWithBlank(songId: String) {
        viewModelScope.launch {
            getSongWithBlankUseCase(songId).collect {
                if(it is ApiResult.Success){
                    endTime = it.data.lyricEnd.toMilliSecond()
                    tmpList.clear()
                    tmpList.addAll(it.data.lyricsBlank)
                    timeLineIndexMap.clear()
                    for((idx, value) in it.data.lyricsBlank.withIndex()){
                        timeLineIndexMap[value.lyricStartTimeStamp.toMilliSecond()] = idx
                    }

                    _blankQuestionList.clear()
                    _blankQuestionList.addAll(it.data.lyricsBlank.map{
                        BlankQuestion(it.lyricBlank, it.lyricBlankAnswer, it.lyricStartTimeStamp, "_".repeat(it.lyricBlankAnswer.length),
                            it.lyricBlank.indexOf("_"), it.lyricBlank.lastIndexOf("_")
                        )
                    })
                }
                _songResult.emit(it)
            }
        }
    }

}