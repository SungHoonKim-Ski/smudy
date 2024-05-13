package com.ssafy.presentation.ui.study.pronounce

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.usecase.study.pronounce.GetPronounceProblemUseCase
import com.ssafy.domain.usecase.study.pronounce.GetTranslateLyricUseCase
import com.ssafy.domain.usecase.study.pronounce.GradePronounceProblemUseCase
import com.ssafy.presentation.model.PronounceProblem
import com.ssafy.presentation.model.pronounce.FormantsAvg
import com.ssafy.presentation.model.pronounce.GradedPronounce
import com.ssafy.presentation.model.pronounce.IntensityData
import com.ssafy.presentation.model.pronounce.LyricAiAnalyze
import com.ssafy.presentation.model.pronounce.PitchData
import com.ssafy.presentation.model.pronounce.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PronounceProblemViewModel @Inject constructor(
    private val getPronounceProblemUseCase: GetPronounceProblemUseCase,
    private val getTranslateLyricUseCase: GetTranslateLyricUseCase,
    private val gradePronounceProblemUseCase: GradePronounceProblemUseCase
) : ViewModel() {
    private val _pronounceProblem =
        MutableStateFlow(PronounceProblem("", "", "", "", emptyList()))
    val pronounceProblem = _pronounceProblem.asStateFlow()
    private var _songId:String = ""

    private var _translatedLyric = emptyList<String>()
    val translateLyric get() = _translatedLyric

    private val _navigationTrigger = MutableSharedFlow<Boolean>(
        replay = 0,
        extraBufferCapacity = 1
    )
    val navigationTrigger: SharedFlow<Boolean> = _navigationTrigger.asSharedFlow()

    lateinit var pronounceResult: GradedPronounce

    private suspend fun triggerNavigation(shouldNavigate: Boolean) {
            _navigationTrigger.emit(shouldNavigate)  // 이벤트 발행
    }
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
            getTranslateLyricUseCase(lyric).collect {
                when (it) {
                    is ApiResult.Success -> {
                        triggerNavigation(true)
                        _translatedLyric=listOf(lyric, it.data)
                    }

                    is ApiResult.Failure -> {

                    }

                    is ApiResult.Loading -> {}
                }
            }
        }
    }

    fun gradePronounceProblem(userFile: File, ttsFile: File) {
        viewModelScope.launch {
            gradePronounceProblemUseCase(
                userFile,
                ttsFile,
                translateLyric[0],
                translateLyric[1],
                _songId
            ).collect {
                when (it) {
                    is ApiResult.Success -> {
                        pronounceResult =
                            GradedPronounce(
                                it.data.userPronounce,
                                it.data.ttsPronounce,
                                it.data.lyricSentenceEn,
                                "임시 데이터",
                                it.data.userLyricSttEn,
                                it.data.userLyricSttKo,
                                LyricAiAnalyze(
                                    FormantsAvg(it.data.lyricAiAnalyze.refFormantsAvg.f1,it.data.lyricAiAnalyze.refFormantsAvg.f2),
                                    IntensityData(it.data.lyricAiAnalyze.refIntensityData.times,it.data.lyricAiAnalyze.refIntensityData.values),
                                    PitchData(it.data.lyricAiAnalyze.refPitchData.times,it.data.lyricAiAnalyze.refPitchData.values),
                                    it.data.lyricAiAnalyze.refTimestamps.map {timeStamp ->
                                                                     Timestamp(timeStamp.time,timeStamp.duration,timeStamp.word)
                                    },
                                    FormantsAvg(it.data.lyricAiAnalyze.testFormantsAvg.f1,it.data.lyricAiAnalyze.testFormantsAvg.f2),
                                    IntensityData(it.data.lyricAiAnalyze.testIntensityData.times,it.data.lyricAiAnalyze.testIntensityData.values),
                                    PitchData(it.data.lyricAiAnalyze.testPitchData.times,it.data.lyricAiAnalyze.testPitchData.values))
                            )

                        triggerNavigation(true)
                    }

                    is ApiResult.Failure -> {}
                    is ApiResult.Loading -> {}
                }
            }
        }
    }

    fun setSongId(id:String){
        _songId = id
    }
}