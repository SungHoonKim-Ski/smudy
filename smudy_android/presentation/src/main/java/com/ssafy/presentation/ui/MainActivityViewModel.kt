package com.ssafy.presentation.ui

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(): ViewModel(

) {

    private val _spotifyActivityResult =  MutableLiveData<Triple<Int, Int, Intent?>>(Triple(0,0,null))
    val spotifyActivityResult: LiveData<Triple<Int, Int, Intent?>>
        get() =  _spotifyActivityResult

    fun setResult(requestCode: Int, resultCode: Int, data: Intent?){
        _spotifyActivityResult.postValue(Triple(requestCode, resultCode, data))
    }

    private val _isNavigationBarVisible = MutableLiveData(true)
    val isNavigationBarVisible: LiveData<Boolean> get() = _isNavigationBarVisible

    fun setIsNavigationBarVisible(isNavigationBarVisible: Boolean) {
        _isNavigationBarVisible.postValue(isNavigationBarVisible)
    }
}