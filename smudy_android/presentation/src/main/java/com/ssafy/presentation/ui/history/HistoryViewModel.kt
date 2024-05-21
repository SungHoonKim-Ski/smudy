package com.ssafy.presentation.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.user.StudyReport
import com.ssafy.domain.usecase.user.GetHistoryUseCase
import com.ssafy.domain.usecase.user.history.GetExpressStudyHistoryUseCase
import com.ssafy.domain.usecase.user.history.GetFillStudyHistoryUseCase
import com.ssafy.domain.usecase.user.history.GetPickStudyHistoryUseCase
import com.ssafy.domain.usecase.user.history.GetPronounceStudyHistoryUseCase
import com.ssafy.presentation.model.Music
import com.ssafy.presentation.model.ParcelableShuffleSubmitResult
import com.ssafy.presentation.model.ParcelableSubmitBlankResult
import com.ssafy.presentation.model.ParcelableSubmitFillBlankData
import com.ssafy.presentation.model.ParcelableSubmitResult
import com.ssafy.presentation.model.express.ExpressResult
import com.ssafy.presentation.model.pronounce.FormantsAvg
import com.ssafy.presentation.model.pronounce.GradedPronounce
import com.ssafy.presentation.model.pronounce.IntensityData
import com.ssafy.presentation.model.pronounce.LyricAiAnalyze
import com.ssafy.presentation.model.pronounce.PitchData
import com.ssafy.presentation.model.pronounce.Timestamp
import com.ssafy.presentation.model.toParcelable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase,
    private val getPickStudyHistoryUseCase: GetPickStudyHistoryUseCase,
    private val getFillStudyHistoryUseCase: GetFillStudyHistoryUseCase,
    private val getExpressStudyHistoryUseCase: GetExpressStudyHistoryUseCase,
    private val getPronounceStudyHistoryUseCase: GetPronounceStudyHistoryUseCase
) : ViewModel() {

    private val _selectedDate = MutableLiveData<LocalDate?>(null)
    val selectedDate: LiveData<LocalDate?>
        get() = _selectedDate

    fun setSelectedDate(date: LocalDate) {
        _selectedDate.postValue(date)
    }

    private val _history = MutableStateFlow<ApiResult<List<StudyReport>>>(ApiResult.Loading())
    val history: StateFlow<ApiResult<List<StudyReport>>> = _history.asStateFlow()

    private val _historyGroup = MutableLiveData<Map<LocalDate, List<StudyReport>>>(mapOf())
    val historyGroup: LiveData<Map<LocalDate, List<StudyReport>>>
        get() = _historyGroup

    private val _currentMonth = MutableLiveData<YearMonth?>(null)
    val currentMonth: LiveData<YearMonth?>
        get() = _currentMonth

    private lateinit var _parcelableShuffleSubmitResult: ParcelableShuffleSubmitResult
    private lateinit var _parcelableSubmitResult: ParcelableSubmitResult
    private lateinit var _expressHistoryResult: List<ExpressResult>
    private lateinit var _pronounceHistoryResult: GradedPronounce
    private lateinit var _selectedMusicInfo: Music

    private val _navigationTrigger = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1
    )
    val navigationTrigger: SharedFlow<String> = _navigationTrigger.asSharedFlow()
    private suspend fun triggerNavigation(shouldNavigate: String) {
        _navigationTrigger.emit(shouldNavigate)  // 이벤트 발행
    }

    fun setCurrentMonth(yearMonth: YearMonth?) {
        _currentMonth.postValue(yearMonth)
    }


    fun getHistory(date: Long) {
        viewModelScope.launch {
            getHistoryUseCase(date).collect {
                _history.emit(it)
            }
        }
    }

    fun groupingHistory(list: List<StudyReport>) {
        _historyGroup.postValue(
            list.groupBy {
                Instant.ofEpochMilli(it.date).atZone(ZoneId.of("Asia/Seoul")).toLocalDate()
            }
        )
    }

    fun getPickHistory(learnReportId: Long, title: String, jacket: String, artist: String) {
        viewModelScope.launch {
            getPickStudyHistoryUseCase(learnReportId.toString()).collect {
                when (it) {
                    is ApiResult.Success -> {
                        _parcelableShuffleSubmitResult = ParcelableShuffleSubmitResult(
                            title,
                            artist,
                            jacket,
                            it.data.totalSize,
                            it.data.score,
                            it.data.correct.map { it.toParcelable() },
                            it.data.wrong.map { it.toParcelable() })
                        triggerNavigation("PICK")
                    }

                    is ApiResult.Failure -> {}
                    is ApiResult.Loading -> {}
                }
            }
        }
    }

    fun getPickHistoryResult(): ParcelableShuffleSubmitResult {
        return _parcelableShuffleSubmitResult
    }

    fun getFillHistory(learnReportId: Long, title: String, jacket: String, artist: String) {
        viewModelScope.launch {
            getFillStudyHistoryUseCase(learnReportId.toString()).collect {
                when (it) {
                    is ApiResult.Success -> {
                        val blankNum = it.data.result.count { res ->
                            res.originWord.isBlank()
                        }
                        _parcelableSubmitResult = ParcelableSubmitResult(title, artist, jacket,
                            ParcelableSubmitFillBlankData(
                                it.data.totalSize,
                                it.data.score - blankNum,
                                it.data.result.map { res ->
                                    ParcelableSubmitBlankResult(
                                        res.lyricBlank,
                                        res.originWord,
                                        res.userWord,
                                        res.isCorrect
                                    )
                                }
                            ))
                        triggerNavigation("FILL")
                    }

                    is ApiResult.Failure -> {}
                    is ApiResult.Loading -> {}
                }
            }
        }
    }

    fun getFillHistoryResult(): ParcelableSubmitResult {
        return _parcelableSubmitResult
    }

    fun getExpressHistory(learnReportId: Long, title: String, jacket: String, artist: String) {
        viewModelScope.launch {
            getExpressStudyHistoryUseCase(learnReportId.toString()).collect {
                when (it) {
                    is ApiResult.Success -> {
                        _selectedMusicInfo = Music(title, artist, jacket)
                        _expressHistoryResult =
                            it.data.map { data ->
                                ExpressResult(
                                    data.suggestLyricSentence,
                                    data.lyricSentenceKo,
                                    data.userLyricSentenceEn,
                                    data.userLyricSentenceKo,
                                    data.score
                                )
                            }
                        triggerNavigation("EXPRESS")
                    }

                    is ApiResult.Failure -> {}
                    is ApiResult.Loading -> {}
                }
            }
        }
    }

    fun getPronounceHistory(learnReportId: Long, title: String, jacket: String, artist: String) {
        viewModelScope.launch {
            getPronounceStudyHistoryUseCase(learnReportId.toString()).collect {
                when (it) {
                    is ApiResult.Success -> {
                        _selectedMusicInfo = Music(title, artist, jacket)
                        with(it.data) {
                            _pronounceHistoryResult = GradedPronounce(
                                lyricSentenceEn,
                                lyricSentenceKo,
                                userLyricSttEn,
                                with(lyricAiAnalyze) {
                                    LyricAiAnalyze(
                                        FormantsAvg(refFormantsAvg.f1, refFormantsAvg.f2),
                                        refIntensityData.map { data ->
                                            IntensityData(data.times, data.values)
                                        },
                                        refPitchData.map { data ->
                                            PitchData(data.times, data.values)
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
                                        testIntensityData.map { data ->
                                            IntensityData(data.times, data.values)
                                        },
                                        testPitchData.map { data ->
                                            PitchData(data.times, data.values)
                                        }
                                    )
                                }
                            )
                        }
                        triggerNavigation("PRONOUNCE")
                    }

                    is ApiResult.Failure -> {}
                    is ApiResult.Loading -> {}
                }
            }
        }
    }

    fun getExpressHistoryResult() = _expressHistoryResult
    fun getSelectedMusicInfo() = _selectedMusicInfo
    fun getPronounceHistoryResult() = _pronounceHistoryResult
}