package com.ssafy.studyservice.service.feign

import com.ssafy.studyservice.dto.request.ai.SimilarityRequest
import com.ssafy.studyservice.dto.response.ai.SimilarityResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.net.URI


@FeignClient(name = "myai")
interface AIFeignClient {


    @PostMapping("/api/ai/similarity")
    fun getSimilarity(baseurl: URI ,@RequestBody request: SimilarityRequest) : String

//    @PostMapping("/pronounce")
//    fun getPronounce()
}