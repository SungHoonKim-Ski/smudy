package com.ssafy.presentation.ui.study.express

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExpressViewModel @Inject constructor(

) : ViewModel() {
    init {

    }

    private lateinit var songId: String

    fun setSongId(id: String) {
        songId = id
    }
}