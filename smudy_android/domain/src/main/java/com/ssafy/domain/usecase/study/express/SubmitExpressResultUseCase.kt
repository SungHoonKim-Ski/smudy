package com.ssafy.domain.usecase.study.express

import com.ssafy.domain.model.ApiResult
import com.ssafy.domain.model.study.express.GradedExpressResultDto
import com.ssafy.domain.repository.ExpressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubmitExpressResultUseCase @Inject constructor(
    private val expressRepository: ExpressRepository
) {
    suspend operator fun invoke(gradedExpressResultDto: GradedExpressResultDto):Flow<ApiResult<Boolean>> {
        return expressRepository.submitExpressProblem(gradedExpressResultDto)
    }
}