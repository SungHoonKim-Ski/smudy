package com.ssafy.presentation.ui.study.pronounce

import androidx.lifecycle.ViewModel
import com.ssafy.domain.usecase.study.pronounce.GetPronounceProblemUseCase
import com.ssafy.domain.usecase.study.pronounce.GetTranslateLyric
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PronouncePracticeViewModel @Inject constructor(
    private val getPronounceProblemUseCase: GetPronounceProblemUseCase,
    private val getTranslateLyric: GetTranslateLyric
):ViewModel() {

}