package com.ssafy.domain.usecase.study.express

import com.ssafy.domain.repository.ExpressRepository
import javax.inject.Inject

class GetExpressProblemInfoUseCase @Inject constructor(
    private val expressRepository: ExpressRepository
) {
    suspend operator fun invoke(songId:String) = expressRepository.getExpressProblem(songId)
}