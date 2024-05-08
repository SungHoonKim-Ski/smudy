package com.ssafy.presentation.ui.study.pronounce

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.usecase.study.pronounce.GetPronounceProblemUseCase
import com.ssafy.domain.usecase.study.pronounce.GetTranslateLyric
import com.ssafy.presentation.model.PronounceProblem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PronouncePracticeViewModel @Inject constructor(
    private val getPronounceProblemUseCase: GetPronounceProblemUseCase,
    private val getTranslateLyric: GetTranslateLyric
):ViewModel() {

}