package com.ssafy.data.repository

import com.ssafy.data.api.LoginInfoRequest
import com.ssafy.data.datasource.auth.AuthRemoteDataSource
import com.ssafy.data.datasource.datastore.PreferencesDataSource
import com.ssafy.domain.model.ApiError
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.repository.AuthRepository
import com.ssafy.util.NetworkException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val preferencesDataSource: PreferencesDataSource
) : AuthRepository {
    override suspend fun login(id: String): Flow<ApiResult<Boolean>> = flow {
        val result = authRemoteDataSource.login(LoginInfoRequest("", userSnsId = id, ""))
        val data = result.getOrNull()
        if (data != null) {
            // 토큰 저장
            preferencesDataSource.setToken(data.data)
            emit(ApiResult.Success(true))
        } else {
            val exception = result.exceptionOrNull() as NetworkException
            if (exception.code == NetworkException.NOT_FOUND_USER) {
                emit(ApiResult.Success(false))
            } else {
                emit(ApiResult.Failure(ApiError(exception.code, exception.message)))
            }
        }
    }.onStart { emit(ApiResult.Loading()) }

    override suspend fun signup(id: String, name: String, image: String): Flow<ApiResult<Boolean>> =
        flow {
            val result = authRemoteDataSource.signup(LoginInfoRequest(image, id, name))
            val data = result.getOrNull()
            if (data != null) {
                // 토큰 저장
                preferencesDataSource.setToken(data.data)
                emit(ApiResult.Success(true))
            } else {
                val exception = result.exceptionOrNull() as NetworkException
                emit(ApiResult.Failure(ApiError(exception.code, exception.message)))
            }
        }.onStart { emit(ApiResult.Loading()) }

    override suspend fun autoLogin(): Flow<ApiResult<Boolean>> = flow {
        val accessToken = preferencesDataSource.getAccessToken()
        if (accessToken != null) {
            val result = authRemoteDataSource.autoLogin(accessToken)
            val data = result.getOrNull()
            if (data != null) {
                emit(ApiResult.Success(true))
            } else {
                val exception = result.exceptionOrNull() as NetworkException
                emit(ApiResult.Failure(ApiError(exception.code, exception.message)))
            }
        } else {
            emit(ApiResult.Failure(ApiError("-1", "token이 존재하지 않습니다.")))
        }
    }.onStart { emit(ApiResult.Loading()) }
}