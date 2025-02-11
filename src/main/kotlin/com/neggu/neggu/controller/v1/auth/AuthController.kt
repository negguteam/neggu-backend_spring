package com.neggu.neggu.controller.v1.auth

import com.neggu.neggu.annotation.PendingUser
import com.neggu.neggu.dto.user.*
import com.neggu.neggu.model.auth.OauthProvider
import com.neggu.neggu.model.auth.RegisterClaims
import com.neggu.neggu.service.user.SocialLoginService
import com.neggu.neggu.service.user.UserRegisterService
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
            registerClaims.profileImage,
            OauthProvider.from(registerClaims.provider),
            userRegisterRequest,
        )
    }

    @PostMapping("/check/nickname")
    override fun checkNickname(
        @RequestBody duplicateNicknameRequest: DuplicateNicknameRequest,
    ): DuplicateNicknameResponse {
        return userRegisterService.checkNickname(duplicateNicknameRequest.nickName)
    }
}