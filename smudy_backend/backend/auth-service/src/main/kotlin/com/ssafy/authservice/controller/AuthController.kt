package com.ssafy.authservice.controller

import com.ssafy.authservice.dto.request.LoginRequest
import com.ssafy.authservice.dto.request.ReissueRequest
import com.ssafy.authservice.dto.request.SignUpRequest
import com.ssafy.authservice.dto.response.TokenResponse
import com.ssafy.authservice.service.AuthService
import com.ssafy.authservice.service.UserService
import com.ssafy.authservice.util.AuthResponseService
import com.ssafy.authservice.util.CommonResult
import com.ssafy.authservice.util.SingleResult
import io.github.oshai.kotlinlogging.KotlinLogging
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.antlr.v4.runtime.Token
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/auth")
class AuthController (
    private val userService: UserService,
    private val authService: AuthService,
    private val responseService: AuthResponseService,
){

    private val logger = KotlinLogging.logger{ }

    @PostMapping("/signup")
    fun signup(@RequestBody request: SignUpRequest)
    : ResponseEntity<SingleResult<TokenResponse>> {

        logger.info { "/signup : $request"}

        val response = userService.registerUser(
                request.userSnsId,
                request.userSnsName,
                request.userImage
        )

        return ResponseEntity(
            responseService.getSuccessSingleResult(
                message = "회원 가입 완료",
                data = response
            ),
            HttpStatus.CREATED
        )
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest)
    : ResponseEntity<SingleResult<TokenResponse>> {

        logger.debug { "/login : $request"}

        val tokenResponse: TokenResponse = authService.login(request)

        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                        tokenResponse
                        ,"로그인 성공"
                )
        )
    }

    @PostMapping("/autologin")
    fun autoLogin(@RequestHeader("Authorization") accessToken: String)
    : ResponseEntity<CommonResult> {

        logger.debug { "/autologin : $accessToken"}

        return if(!authService.autoLogin(accessToken)) {
            ResponseEntity.ok(
                    responseService.getSuccessResult("자동 로그인 성공")
            )
        } else {
            val errorResponse = CommonResult(
                code = 403,
                message = "만료된 토큰 입니다."
            )

            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse)
        }
    }

    @PostMapping("/reissue")
    fun reissue(@RequestHeader("Authorization") requestAccessTokenInHeader: String,
                @RequestBody request: ReissueRequest
    )
    : ResponseEntity<CommonResult> {

        logger.debug { "/reissue: ${requestAccessTokenInHeader}, refreshToken: ${request.refreshToken}" }

        val tokenResponse = authService.reissue(requestAccessTokenInHeader, request.refreshToken)

        return if (tokenResponse != null) {
            ResponseEntity.ok(
                responseService.getSuccessSingleResult(tokenResponse, "토큰 재발급 성공")
            )
        } else {
            val errorResponse = CommonResult(
                code = 403,
                message = "재로그인 필요"
            )

            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse)
        }
    }
}