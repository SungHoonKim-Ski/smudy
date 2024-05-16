package com.ssafy.data.datasource.study.remote.express

import com.ssafy.data.model.DefaultResponse
import com.ssafy.data.model.music.express.ExpressAnswerRequest
import com.ssafy.data.model.music.express.ExpressGradedResultResponse
import com.ssafy.data.model.music.express.ExpressProblemResponse
import com.ssafy.data.model.music.express.ExpressResultRequest

interface ExpressRemoteDataSource {
    suspend fun getExpressProblem(songId:String): Result<DefaultResponse<ExpressProblemResponse>>
    suspend fun checkExpressProblem(expressAnswerRequest: ExpressAnswerRequest):Result<DefaultResponse<ExpressGradedResultResponse>>
    suspend fun submitExpressProblem(expressResultRequest: ExpressResultRequest):Result<DefaultResponse<Boolean>>
}