package com.ssafy.presentation.ui.study.fill

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.LyricBlank
import com.ssafy.domain.model.SongWithBlank
import com.ssafy.domain.model.SubmitFillBlankData
import com.ssafy.domain.usecase.music.SubmitFillBlankUseCase
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

private const val TAG = "FillFragmentViewModel"

@HiltViewModel
class FillFragmentViewModel @Inject constructor(
    private val getSongWithBlankUseCase: GetSongWithBlankUseCase,
    private val submitFillBlankUseCase: SubmitFillBlankUseCase
): ViewModel() {
    private lateinit var timer : Job

    private val _songResult = MutableStateFlow<ApiResult<SongWithBlank>>(ApiResult.Loading())
    val songResult = _songResult.asStateFlow()

    private val _submitResult = MutableStateFlow<ApiResult<SubmitFillBlankData>>(ApiResult.Loading())
    val submitResult = _submitResult.asStateFlow()

    val timeLineIndexMap = mutableMapOf<Long, Int>()
    private val _blankQuestionList = mutableListOf<BlankQuestion>()

    private var _numOfQuestions = 0
    val numOfQuestion: Int get() = _numOfQuestions

    private var _playerSate = PlayState.INIT
    val playerState: PlayState
        get() = _playerSate

    fun setPlayerState(state: PlayState){
        _playerSate = state
    }

    val blankQuestionList: List<BlankQuestion>
        get() = _blankQuestionList


    fun setQuestionInput(input: String, idx: Int){
        _blankQuestionList[idx] = _blankQuestionList[idx].copy(
            inputAnswer = input
        )
        Log.d(TAG, "setQuestionInput: ${_blankQuestionList[idx]}")
    }

    private var songId = ""
    fun setSongId(songId: String){
        this.songId = songId
    }

    fun submitAnswer(){
        viewModelScope.launch {
            submitFillBlankUseCase(songId, _blankQuestionList.map{ it.inputAnswer }).collect{
                if(it is ApiResult.Success){

                }
                _submitResult.emit(it)
            }
        }

    }

    fun getQuestionInput(idx: Int) = _blankQuestionList[idx].inputAnswer

    private var endTime = -1L

    private var _curLine = 0
    val curLine: Int get() = _curLine

    fun setCurLine(curLine: Int){
        _curLine = curLine
    }

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
                    timeLineIndexMap.clear()
                    timeLineIndexMap[0L] = -1
                    for((idx, value) in it.data.lyricsBlank.withIndex()){
                        timeLineIndexMap[value.lyricStartTimeStamp.toMilliSecond()] = idx
                    }

                    _blankQuestionList.clear()
                    _blankQuestionList.addAll(it.data.lyricsBlank.map{
                        BlankQuestion(it.lyricBlank.replace("_", " "), it.lyricBlankAnswer, it.lyricStartTimeStamp.toMilliSecond(), " ".repeat(it.lyricBlankAnswer.length),
                            it.lyricBlank.indexOf("_"), it.lyricBlank.lastIndexOf("_")
                        )
                    })
                    _numOfQuestions = it.data.lyricsBlank.size
                }
                _songResult.emit(it)
            }
        }
    }

}