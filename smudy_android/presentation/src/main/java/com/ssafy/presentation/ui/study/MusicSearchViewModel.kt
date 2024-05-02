package com.ssafy.presentation.ui.study

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.usecase.music.SearchKeywordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicSearchViewModel @Inject constructor(
    private val searchKeywordUseCase: SearchKeywordUseCase
) : ViewModel() {

    private val _keywords = MutableStateFlow<List<String>>(emptyList())
    val keywords = _keywords.asStateFlow()

    fun searchKeyword(query: String) {
        viewModelScope.launch {
            searchKeywordUseCase(query).collect {
                when (it) {
                    is ApiResult.Success -> {
                        _keywords.emit(it.data)
                    }

                    is ApiResult.Failure -> {

                    }

                    is ApiResult.Loading -> {}
                }
            }
        }
    }
}