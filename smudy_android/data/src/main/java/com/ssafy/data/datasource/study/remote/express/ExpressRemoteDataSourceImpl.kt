package com.ssafy.data.datasource.study.remote.express

import com.ssafy.data.api.ExpressService
import com.ssafy.data.model.music.express.ExpressAnswerRequest
import javax.inject.Inject

class ExpressRemoteDataSourceImpl @Inject constructor(
    private val expressService: ExpressService
) : ExpressRemoteDataSource {
    override suspend fun getExpressProblem(songId: String) =
        expressService.getExpressProblem(songId)

    override suspend fun checkExpressProblem(expressAnswerRequest: ExpressAnswerRequest) =
        expressService.checkExpressProblem(expressAnswerRequest)
}