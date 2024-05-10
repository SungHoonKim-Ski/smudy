package com.ssafy.studyservice.service.feign

import com.ssafy.studyservice.dto.request.ai.SimilarityRequest
import com.ssafy.studyservice.dto.response.ai.SimilarityResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping


@FeignClient(name = "myai", url = "\${ai.server}", path = "/api/ai" )
interface AIFeignClient {


    @PostMapping("/similarity")
    fun getSimilarity(request: SimilarityRequest) : SimilarityResponse

//    @PostMapping("/pronounce")
//    fun getPronounce()
}