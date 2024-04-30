package com.ssafy.data.repository

import com.ssafy.data.datasource.user.remote.UserInfoRemoteDataSource
import com.ssafy.data.mapper.toDomain
import com.ssafy.domain.model.ApiError
import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.user.UserInfo
import com.ssafy.domain.repository.UserRepository
import com.ssafy.util.NetworkException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
   private val userInfoRemoteDataSource: UserInfoRemoteDataSource
): UserRepository {
    override suspend fun getUserInfo(): Flow<ApiResult<UserInfo>> = flow {
        val result = userInfoRemoteDataSource.getUserInfo()
        val data = result.getOrNull()
        if(data!= null) {
            emit(ApiResult.Success(data.toDomain()))
        }else{
            val exception = result.exceptionOrNull() as NetworkException
            emit(ApiResult.Failure(ApiError(exception.code, exception.message)))
        }
    }.onStart { emit(ApiResult.Loading()) }
}