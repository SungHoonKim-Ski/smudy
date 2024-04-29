package com.ssafy.backend_dummy.auth_service.controller

import com.ssafy.backend_dummy.auth_service.dto.request.LoginRequest
import com.ssafy.backend_dummy.auth_service.dto.request.SignUpRequest
import com.ssafy.backend_dummy.auth_service.dto.response.TokenResponse
import com.ssafy.backend_dummy.auth_service.util.CommonResult
import com.ssafy.backend_dummy.auth_service.util.SingleResult
import com.ssafy.backend_dummy.auth_service.util.AuthResponseService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/api/auth")
class AuthController (
    private val responseService: AuthResponseService
){

    private val logger = KotlinLogging.logger{ }
    @GetMapping("/test")
    fun test(): ResponseEntity<SingleResult<String>> {
        var data = "auth"
        logger.debug { "Hello! $data" }
        return ResponseEntity.ok(responseService.getSuccessSingleResult("데이터","성공"))
    }

    @PostMapping("/signup")
    fun signup(
            @RequestBody request: SignUpRequest
    )
    : ResponseEntity<SingleResult<TokenResponse>> {

        logger.debug { "/signup : $request"}
        return ResponseEntity(
                responseService.getSingleResult(
                        TokenResponse(
                                grantType = "Bearer",
                                accessToken = "Bearer_adfgnklsdfgnklsdfdkssudgktpdy",
                                refreshToken = "qksrkqtmqslsdfnklsdfsdfjklsdfjklek"
                        )
                        ,"회원 가입 성공"
                        ,HttpStatus.CREATED.value()
                ),
                HttpStatus.CREATED
        )
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<SingleResult<TokenResponse>> {
        logger.debug { "/login : $request"}
        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                        TokenResponse(
                                grantType = "Bearer",
                                accessToken = "Bearer_adfgnklsdfgnklsdfdkssudgktpdy",
                                refreshToken = "qksrkqtmqslsdfnklsdfsdfjklsdfjklek"
                        )
                        ,"로그인 성공"
                )
        )
    }

    @PostMapping("/autologin")
    fun autoLogin(@RequestHeader("Authorization") accessToken: String): ResponseEntity<CommonResult> {
        logger.debug { "/autologin : $accessToken"}
        return ResponseEntity.ok(
                responseService.getSuccessResult("자동 로그인 성공")
        )
    }

    @PostMapping("/reissue")
    fun reissue(@RequestHeader("Authorization") refreshToken: String): ResponseEntity<SingleResult<TokenResponse>> {
        logger.debug { "/reissue : $refreshToken"}
        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                        TokenResponse(
                                grantType = "Bearer",
                                accessToken = "Bearer_adfgnklsdfgnklsdfdkssudgktpdy",
                                refreshToken = "qksrkqtmqslsdfnklsdfsdfjklsdfjklek"
                        )
                        ,"토큰 재발급 성공"
                )
        )
    }
}