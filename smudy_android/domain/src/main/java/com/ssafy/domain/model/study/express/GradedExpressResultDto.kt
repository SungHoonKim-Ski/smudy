package com.ssafy.domain.model.study.express

data class GradedExpressResultDto(
    val problemBoxId:Int,
    val songId:String,
    val userExpress:List<ExpressResultDto>
)
