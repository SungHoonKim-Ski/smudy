package com.ssafy.userservice.service.feign

import com.ssafy.userservice.dto.response.ai.LyricAiAnalyze
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile
import java.net.URI


@FeignClient(name = "myAI", url = "https://sample.site")
interface AIFeignClient {

    @PostMapping("/api/ai/pronounce", consumes = ["multipart/form-data"])
    fun analyzeUserAndTTS(
            baseurl: URI,
            @RequestPart("userPronounce") userPronounce: MultipartFile,
            @RequestPart("ttsPronounce") ttsPronounce: MultipartFile
    ): LyricAiAnalyze
}