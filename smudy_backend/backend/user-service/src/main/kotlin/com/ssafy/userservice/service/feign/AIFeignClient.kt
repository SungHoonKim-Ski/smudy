package com.ssafy.userservice.service.feign

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