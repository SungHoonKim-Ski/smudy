package com.ssafy.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.usecase.auth.AutoLoginUserUseCase
import com.ssafy.domain.usecase.auth.LoginUserUseCase
import com.ssafy.domain.usecase.auth.SignupUserUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val signupUserUserCase: SignupUserUserCase,
    private val autoLoginUserUseCase: AutoLoginUserUseCase
) : ViewModel() {

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess =  _loginSuccess.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            autoLoginUserUseCase().collect{
                if (it is ApiResult.Success) _loginSuccess.emit(true)
            }
        }
    }
    fun login(id: String, name: String?, image: String?) {
        viewModelScope.launch {
            loginUserUseCase(id).collect {
                when (it) {
                    is ApiResult.Success -> {
                        if (it.data) {
                            _isLoading.emit(false)
                            _loginSuccess.emit(true)
                        } else signup(id, name, image)
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

    private fun signup(id: String, name: String?, image: String?) {
        viewModelScope.launch {
            signupUserUserCase(id, name ?: "", image ?: "").collect {
                when (it) {
                    is ApiResult.Success -> {
                        _isLoading.emit(false)
                        _loginSuccess.emit(true)
                    }

                    is ApiResult.Loading -> _isLoading.emit(true)
                    is ApiResult.Failure -> _isLoading.emit(false)
                }
            }
        }
    }
}