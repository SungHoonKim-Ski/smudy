package com.ssafy.presentation.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.user.StudyReport
import com.ssafy.domain.usecase.user.GetHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase
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

}