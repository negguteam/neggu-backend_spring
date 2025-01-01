package com.neggu.neggu.service.user

import com.neggu.neggu.model.auth.UserClaims
import com.neggu.neggu.repository.RefreshTokenRepository
import com.neggu.neggu.service.jwt.JwtProvider
import org.springframework.stereotype.Service

@Service
class LogoutService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtProvider: JwtProvider,
) {
    fun logout(refreshToken: String) {
        val claims: UserClaims = jwtProvider.resolveRefreshToken(refreshToken)
        refreshTokenRepository.deleteByUserId(claims.id)
    }
}