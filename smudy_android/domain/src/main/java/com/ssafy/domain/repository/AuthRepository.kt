package com.ssafy.domain.repository

import com.ssafy.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(id: String): Flow<ApiResult<Boolean>>
}