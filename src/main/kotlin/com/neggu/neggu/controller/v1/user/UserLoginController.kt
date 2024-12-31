package com.neggu.neggu.controller.v1.user

import com.neggu.neggu.config.LoginUser
import com.neggu.neggu.dto.user.IdTokenRequest
import com.neggu.neggu.dto.user.SocialLoginResponse
import com.neggu.neggu.dto.user.TokenRequest
import com.neggu.neggu.model.user.OauthProvider
import com.neggu.neggu.model.user.User
import com.neggu.neggu.service.user.SocialLoginService
import com.neggu.neggu.service.user.LogoutService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class UserLoginController(
    private val socialLoginService: SocialLoginService,
    private val logoutService: LogoutService,
) : UserLoginApi {

    @PostMapping("/login/{provider}")
    override fun login(
        @PathVariable provider: String,
        @RequestBody idTokenRequest: IdTokenRequest,
    ): SocialLoginResponse {
        return socialLoginService.socialLogin(OauthProvider.from(provider), idTokenRequest.idToken)
    }

    @Secured
    @PostMapping("/logout")
    override fun logout(
        @LoginUser user: User,
        @RequestBody tokenRequest: TokenRequest,
    ): ResponseEntity<Void> {
        logoutService.logout(tokenRequest.refreshToken)
        return ResponseEntity.noContent().build()
    }
}