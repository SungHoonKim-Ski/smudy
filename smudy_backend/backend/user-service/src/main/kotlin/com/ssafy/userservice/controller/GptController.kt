package com.ssafy.userservice.controller

import com.ssafy.userservice.service.OpenAIService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/gpt")
class GptController(
        private val openAIService: OpenAIService,
) {

    private val logger = KotlinLogging.logger {  }

    @GetMapping("/translate")
    fun translateLyric(@RequestParam("lyric") lyric: String): String {
        return openAIService.translateLyric(lyric)
    }

//    @GetMapping("/translateall")
//    fun translateAll() {
//        openAIService.translateAllProblems()
//    }

}