package com.ssafy.userservice.controller

import com.ssafy.userservice.dto.request.SignUpRequest
import com.ssafy.userservice.dto.response.*
import com.ssafy.userservice.dto.response.feign.FillResponse
import com.ssafy.userservice.dto.response.feign.PronounceResponse
import com.ssafy.userservice.service.SongService
import com.ssafy.userservice.service.UserService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/user/feign")
class FeignController (
        private val songService: SongService,
        private val userService: UserService,
){
    private val logger = KotlinLogging.logger{ }

    @GetMapping("/fill/{songId}")
    fun getFillQuiz(@PathVariable("songId") songId: String) : FillResponse {
        return songService.getFillQuiz(songId)
    }

    @GetMapping("/pick/{songId}")
    fun getPickQuiz(@PathVariable("songId") songId: String) : SongSimple {
        return songService.getPickQuiz(songId)
    }

    @GetMapping("/express/{songId}")
    fun getExpressQuiz(@PathVariable("songId") songId: String) : SongSimple {
        return songService.getExpressQuiz(songId)
    }

    @GetMapping("/pronounce/{songId}")
    fun getPronounceQuiz(@PathVariable("songId") songId: String) : PronounceResponse {
        return songService.getPronounceQuiz(songId)
    }

}