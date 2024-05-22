package com.ssafy.presentation.ui.study.pronounce

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.user.UserInfo
import com.ssafy.domain.usecase.study.pronounce.GetPronounceProblemUseCase
import com.ssafy.domain.usecase.study.pronounce.GetTranslateLyricUseCase
import com.ssafy.domain.usecase.study.pronounce.GradePronounceProblemUseCase
import com.ssafy.domain.usecase.user.GetUserInfoUseCase
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
    private val gradePronounceProblemUseCase: GradePronounceProblemUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {
    private val _pronounceProblem =
        MutableStateFlow(PronounceProblem("", "", "", "", emptyList()))
    val pronounceProblem = _pronounceProblem.asStateFlow()
    private var _songId: String = ""

    private var _translatedLyric = emptyList<String>()
    val translateLyric get() = _translatedLyric
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _navigationTrigger = MutableSharedFlow<Boolean>(
        replay = 0,
        extraBufferCapacity = 1
    )
    val navigationTrigger: SharedFlow<Boolean> = _navigationTrigger.asSharedFlow()

    lateinit var pronounceResult: GradedPronounce

    private val _userInfo = MutableStateFlow(UserInfo("", "", 0, emptyList()))
    val userInfo = _userInfo.asStateFlow()

    private suspend fun triggerNavigation(shouldNavigate: Boolean) {
        _navigationTrigger.emit(shouldNavigate)  // 이벤트 발행
    }

    fun getPronounceProblem(id: String) {
        viewModelScope.launch {
            getPronounceProblemUseCase(id).collect {
                when (it) {
                    is ApiResult.Success -> {
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

    init {
        viewModelScope.launch {
            getUserInfoUseCase().collect {
                when (it) {
                    is ApiResult.Success -> {
                        _userInfo.emit(UserInfo(it.data.name, it.data.img, 0, emptyList()))
                    }

                    is ApiResult.Failure -> {}
                    is ApiResult.Loading -> {}
                }
            }
        }
    }

    fun getTranslateLyric(lyricPosition: Int) {
        viewModelScope.launch {
            _isLoading.emit(true)
            val lyric = _pronounceProblem.value.lyrics[lyricPosition]
            getTranslateLyricUseCase(lyric).collect {
                when (it) {
                    is ApiResult.Success -> {
                        _translatedLyric = listOf(lyric, it.data)
                        _isLoading.emit(false)
                        triggerNavigation(true)
                    }

                    is ApiResult.Failure -> {
                        _isLoading.emit(false)
                    }

                    is ApiResult.Loading -> {
                        _isLoading.emit(true)
                    }
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
                            with(it.data){
                                GradedPronounce(
                                    lyricSentenceEn,
                                    lyricSentenceKo,
                                    userLyricSttEn,
                                    with(lyricAiAnalyze){
                                        LyricAiAnalyze(
                                            FormantsAvg(
                                                refFormantsAvg.f1,
                                                refFormantsAvg.f2
                                            ),
                                            refIntensityData.map {data ->
                                                IntensityData(
                                                    data.times,
                                                    data.values
                                                )
                                            },
                                            refPitchData.map { data ->
                                                PitchData(
                                                    data.times,
                                                    data.values
                                                )
                                            },
                                            refTimestamps.map { timeStamp ->
                                                Timestamp(
                                                    timeStamp.word,
                                                    timeStamp.startTime,
                                                    timeStamp.endTime
                                                )
                                            },
                                            FormantsAvg(
                                                testFormantsAvg.f1,
                                                lyricAiAnalyze.testFormantsAvg.f2
                                            ),
                                            testIntensityData.map {data ->
                                                IntensityData(
                                                    data.times,
                                                    data.values
                                                )
                                            },
                                            testPitchData.map {data ->
                                                PitchData(
                                                    data.times,
                                                    data.values
                                                )
                                            }
                                        )
                                    }
                                )
                            }
                        _isLoading.emit(false)
                        triggerNavigation(true)
                    }

                    is ApiResult.Failure -> { _isLoading.emit(false) }
                    is ApiResult.Loading -> {
                        _isLoading.emit(true)
                    }
                }
            }
        }
    }

    fun setSongId(id: String) {
        _songId = id
    }
}