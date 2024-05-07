package com.ssafy.studyservice.service.feign

import com.ssafy.studyservice.dto.response.FillResponse
import com.ssafy.studyservice.dto.response.PronounceResponse
import com.ssafy.studyservice.dto.response.SongSimple
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "user-service", path = "/api/user/feign")
interface UserFeignClient {

    @GetMapping("/fill/{songId}")
    fun getFillQuiz(@PathVariable("songId") songId: String) : FillResponse

    @GetMapping("/pick/{songId}")
    fun getPickQuiz(@PathVariable("songId") songId: String) : SongSimple

    @GetMapping("/express/{songId}")
    fun getExpressQuiz(@PathVariable("songId") songId: String) : SongSimple

    @GetMapping("/pronounce/{songId}")
    fun getPronounceQuiz(@PathVariable("songId") songId: String) : PronounceResponse
}