package com.ssafy.presentation.ui.history

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(): ViewModel() {

    var selectedDate: LocalDate? = null

}