package com.ssafy.presentation.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.usecase.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess = _loginSuccess

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading

    fun login(id: String) {
        viewModelScope.launch {
            loginUserUseCase(id).collect {
                when (it) {
                    is ApiResult.Success -> {
                        _isLoading.emit(false)
                        if (it.data) loginSuccess.emit(true) else Log.e("TAG", "login: 회원가입 호출")
                    }

                    is ApiResult.Loading -> {
                        _isLoading.emit(true)
                    }

                    is ApiResult.Failure -> {
                        _isLoading.emit(false)
                    }
                }
            }
        }
    }
}