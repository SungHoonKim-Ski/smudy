package com.ssafy.presentation.ui.main

import androidx.lifecycle.ViewModel
import com.ssafy.domain.usecase.user.GetUserInfoUseCase
import com.ssafy.presentation.model.Profile
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val userInfoUseCase: GetUserInfoUseCase
): ViewModel(){

    val profile: Profile? = null




}