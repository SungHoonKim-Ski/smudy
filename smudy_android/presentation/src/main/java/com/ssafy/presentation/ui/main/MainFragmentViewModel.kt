package com.ssafy.presentation.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.Lyric
import com.ssafy.domain.model.user.Song
import com.ssafy.domain.model.user.Streak
import com.ssafy.domain.model.user.UserInfo
import com.ssafy.domain.usecase.user.GetMainPageLyricUseCase
import com.ssafy.domain.usecase.user.GetRecommendedSongsUseCase
import com.ssafy.domain.usecase.user.GetStreakUseCase
import com.ssafy.domain.usecase.user.GetUserInfoUseCase
import com.ssafy.presentation.model.Profile
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainFragmentViewModel"
@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val userInfoUseCase: GetUserInfoUseCase,
    private val streakUseCase: GetStreakUseCase,
    private val lyricUseCase: GetMainPageLyricUseCase,
    private val recommendedSongsUseCase: GetRecommendedSongsUseCase
): ViewModel(){

    private val _userInfo = MutableStateFlow<ApiResult<UserInfo>>(ApiResult.Loading())
    val userInfo: StateFlow<ApiResult<UserInfo>> = _userInfo.asStateFlow()

    private val _streak = MutableStateFlow<ApiResult<List<Streak>>>(ApiResult.Loading())
    val streak: StateFlow<ApiResult<List<Streak>>> = _streak.asStateFlow()

    private val _wrongLyric = MutableStateFlow<ApiResult<Lyric>>(ApiResult.Loading())
    val wrongLyric: StateFlow<ApiResult<Lyric>> = _wrongLyric.asStateFlow()

    private val _dailyLyric = MutableStateFlow<ApiResult<Lyric>>(ApiResult.Loading())
    val dailyLyric: StateFlow<ApiResult<Lyric>> = _dailyLyric.asStateFlow()

    private val _recommendedSongs = MutableStateFlow<ApiResult<List<Song>>>(ApiResult.Loading())
    val recommendedSongs: StateFlow<ApiResult<List<Song>>> = _recommendedSongs.asStateFlow()

    private val _dailyIsEnglish = MutableLiveData(true)

    val dailyIsEnglish: LiveData<Boolean>
        get() = _dailyIsEnglish

    private val _wrongIsEnglish = MutableLiveData(true)
    val wrongIsEnglish: LiveData<Boolean>
        get() = _wrongIsEnglish

    private val _dailyLyricValue:MutableLiveData<Lyric> = MutableLiveData(Lyric())
    val dailyLyricValue: LiveData<Lyric>
        get() = _dailyLyricValue

    private val _wrongLyricValue:MutableLiveData<Lyric> = MutableLiveData(Lyric())
    val wrongLyricValue: LiveData<Lyric>
        get() = _wrongLyricValue

    fun getUserInfo(){
        viewModelScope.launch {
            userInfoUseCase().collect{
                _userInfo.emit(it)
            }
        }
    }

    fun getStreak(){
        viewModelScope.launch {
            streakUseCase().collect{
                _streak.emit(it)
            }
        }
    }

    fun getWrongLyric(){
        viewModelScope.launch {
            lyricUseCase(false).collect{
                _wrongLyric.emit(it)
            }
        }
    }

    fun getDailyLyric(){
        viewModelScope.launch {
            lyricUseCase(true).collect{
                _dailyLyric.emit(it)

            }
        }
    }

    fun getRecommendedSongs(){
        viewModelScope.launch {
            recommendedSongsUseCase().collect{
                _recommendedSongs.emit(it)
            }
        }
    }

    fun setDailyIsEnglish(){
        _dailyIsEnglish.postValue(
           !_dailyIsEnglish.value!!
        )
    }

    fun setWrongIsEnglish(){
        _wrongIsEnglish.postValue(
            !_wrongIsEnglish.value!!
        )
    }

    fun setDailyLyric(lyric: Lyric){
        _dailyLyricValue.postValue(lyric)
        _dailyIsEnglish.postValue(true)
    }

    fun setWrongLyric(lyric: Lyric){
        _wrongLyricValue.postValue(lyric)
        _wrongIsEnglish.postValue(true)
    }




}