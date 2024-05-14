package com.ssafy.userservice.service.feign

import com.ssafy.userservice.dto.response.ai.LyricAiAnalyze
import com.ssafy.userservice.dto.response.ai.PronounceAnalyzeResponse
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
            @RequestPart("test_file") userPronounce: MultipartFile,
            @RequestPart("ref_file") ttsPronounce: MultipartFile
    ): PronounceAnalyzeResponse
}