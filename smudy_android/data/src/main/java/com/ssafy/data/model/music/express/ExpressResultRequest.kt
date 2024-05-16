package com.ssafy.data.model.music.express

data class ExpressResultRequest(
    val problemBoxId:Int,
    val songId:String,
    val userExpresses:List<GradedExpressResultRequest>
)
