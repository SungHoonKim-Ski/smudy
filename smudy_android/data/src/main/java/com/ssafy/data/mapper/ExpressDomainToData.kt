package com.ssafy.data.mapper

import com.ssafy.data.model.music.express.ExpressResultRequest
import com.ssafy.data.model.music.express.GradedExpressResultRequest
import com.ssafy.domain.model.study.express.ExpressResultDto
import com.ssafy.domain.model.study.express.GradedExpressResultDto

fun GradedExpressResultDto.toExpressResultRequest(): ExpressResultRequest{
    return ExpressResultRequest(problemBoxId,songId,userExpress.map { it.toGradedExpressResultRequest() })
}

fun ExpressResultDto.toGradedExpressResultRequest():GradedExpressResultRequest{
    return GradedExpressResultRequest(suggestLyricSentence, userAnswerSentenceEn, userAnswerSentenceKo, score)
}