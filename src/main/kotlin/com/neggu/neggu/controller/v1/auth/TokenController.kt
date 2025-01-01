package com.neggu.neggu.controller.v1.auth

import com.neggu.neggu.dto.user.TokenRequest
import com.neggu.neggu.dto.user.TokenResponse
import com.neggu.neggu.service.auth.token.TokenService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth/token")
class TokenController(
    private val tokenService: TokenService,
) : TokenApi {

    @PostMapping
    override fun reissueToken(
        @RequestBody tokenRequest: TokenRequest,
    ): TokenResponse {
        return tokenService.reissueToken(tokenRequest.refreshToken)
    }
}