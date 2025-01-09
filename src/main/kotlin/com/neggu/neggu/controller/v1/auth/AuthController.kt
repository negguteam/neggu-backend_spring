package com.neggu.neggu.controller.v1.auth

import com.neggu.neggu.annotation.PendingUser
import com.neggu.neggu.config.LoggerConfig.log
import com.neggu.neggu.config.LoggerConfig.nInfo
import com.neggu.neggu.dto.user.IdTokenRequest
import com.neggu.neggu.dto.user.SocialLoginResponse
import com.neggu.neggu.dto.user.TokenResponse
import com.neggu.neggu.dto.user.UserRegisterRequest
import com.neggu.neggu.model.auth.RegisterClaims
import com.neggu.neggu.model.auth.OauthProvider
import com.neggu.neggu.service.user.SocialLoginService
import com.neggu.neggu.service.user.UserRegisterService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val socialLoginService: SocialLoginService,
    private val userRegisterService: UserRegisterService,
) : AuthApi {

    @PostMapping("/login/{provider}")
    override fun login(
        @PathVariable provider: String,
        @RequestBody idTokenRequest: IdTokenRequest,
    ): SocialLoginResponse {
        return socialLoginService.socialLogin(OauthProvider.from(provider), idTokenRequest.idToken)
    }

    @PostMapping("/register")
    override fun register(
        @PendingUser registerClaims: RegisterClaims,
        @RequestBody userRegisterRequest: UserRegisterRequest,
    ): TokenResponse {
        return userRegisterService.registerUser(
            registerClaims.email,
            OauthProvider.from(registerClaims.provider),
            userRegisterRequest,
        )
    }

    @PostMapping("/oauth/apple")
    fun handleAppleOAuthCallback(
        @RequestParam("code") code: String,
        @RequestParam("id_token", required = false) idToken: String?, // ID 토큰 (선택적)
    ): String {
        log.nInfo("Apple Oauth -> code : $code idToken(not valid yet) : $idToken")
        return code
    }
}