package com.ssafy.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.user.UserInfo
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

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val userInfoUseCase: GetUserInfoUseCase
): ViewModel(){

    private val _userInfo = MutableStateFlow<ApiResult<UserInfo>>(ApiResult.Loading())
    val userInfo: StateFlow<ApiResult<UserInfo>> = _userInfo.asStateFlow()

    fun getUserInfo(){
        viewModelScope.launch {
            userInfoUseCase().collect{
                _userInfo.update{ it }
            }
        }
    }

}