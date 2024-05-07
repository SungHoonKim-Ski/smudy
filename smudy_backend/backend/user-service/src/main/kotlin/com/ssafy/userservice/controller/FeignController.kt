package com.ssafy.userservice.controller

import com.ssafy.userservice.dto.request.SignUpRequest
import com.ssafy.userservice.dto.request.SongIdsRequest
import com.ssafy.userservice.dto.response.*
import com.ssafy.userservice.service.SongService
import com.ssafy.userservice.service.UserService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/user/feign")
class FeignController (
        private val songService: SongService,
        private val userService: UserService
){
    private val logger = KotlinLogging.logger{ }

    @PostMapping("/songs")
    fun getSongs(@RequestBody request: SongIdsRequest) : List<SongSimple> {
        return songService.findAllBySongIds(request.songIds)
    }

    @PostMapping("/signup")
    fun signup(@RequestBody request: SignUpRequest) : String {
        return userService.signup(request)
    }
}