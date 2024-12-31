package com.neggu.neggu.service.user

import com.neggu.neggu.dto.user.SocialLoginResponse
import com.neggu.neggu.model.user.OauthProvider
import com.neggu.neggu.model.user.OidcUser
import com.neggu.neggu.model.user.RefreshToken
import com.neggu.neggu.model.user.User
import com.neggu.neggu.repository.RefreshTokenRepository
import com.neggu.neggu.repository.UserRepository
import com.neggu.neggu.service.oauth.OpenIdTokenResolverSelector
import com.neggu.neggu.service.jwt.JwtProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SocialLoginService(
    val jwtProvider: JwtProvider,
    val openIdTokenResolverSelector: OpenIdTokenResolverSelector,
    val userRepository: UserRepository,
    val refreshTokenRepository: RefreshTokenRepository,
) {

    fun socialLogin(
        provider: OauthProvider,
        idToken: String,
    ): SocialLoginResponse {
        val oidcUser = resolveOidcUser(provider, idToken)

        return userRepository.findByNicknameAndOauthProvider(oidcUser.nickname, provider)
            ?.let { user -> processExistingUser(user) }
            ?: createPendingRegistrationResponse(oidcUser.nickname, provider)
    }

    private fun resolveOidcUser(
        provider: OauthProvider,
        idToken: String,
    ): OidcUser {
        val openIdTokenResolver = openIdTokenResolverSelector.select(provider)
        return openIdTokenResolver.resolveIdToken(idToken)
    }

    private fun processExistingUser(user: User): SocialLoginResponse.Success {
        val refreshToken = generateAndSaveRefreshToken(user)
        return SocialLoginResponse.Success(
            accessToken = jwtProvider.generateAccessToken(user.id, user.nickname),
            expiresIn = jwtProvider.getExpiredIn(),
            refreshToken = refreshToken,
            refreshTokenExpiresIn = jwtProvider.getRefreshExpiredIn(),
        )
    }

    private fun createPendingRegistrationResponse(
        nickname: String,
        provider: OauthProvider,
    ): SocialLoginResponse.Pending {
        return SocialLoginResponse.Pending(
            registerToken = jwtProvider.generateRegisterToken(nickname, provider),
        )
    }

    private fun generateAndSaveRefreshToken(user: User): String {
        val refreshToken = jwtProvider.generateRefreshToken(user.id, user.nickname)
        val refreshTokenExpiresIn = jwtProvider.getRefreshExpiredIn()
        refreshTokenRepository.save(RefreshToken.create(user.id!!, refreshToken, refreshTokenExpiresIn))
        return refreshToken
    }
}