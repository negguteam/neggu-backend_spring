package com.neggu.neggu.service.auth.token

import com.neggu.neggu.dto.user.TokenResponse
import com.neggu.neggu.exception.ErrorType
import com.neggu.neggu.exception.UnAuthorizedException
import com.neggu.neggu.model.auth.UserClaims
import com.neggu.neggu.model.auth.RefreshToken
import com.neggu.neggu.repository.RefreshTokenRepository
import com.neggu.neggu.service.jwt.JwtProvider
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val jwtProvider: JwtProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
) {

    fun reissueToken(refreshToken: String): TokenResponse {
        val userClaims = jwtProvider.resolveRefreshToken(refreshToken)
        validateToken(refreshToken, userClaims.id)

        val accessToken = generateAccessToken(userClaims)
        val newRefreshToken = generateAndStoreRefreshToken(userClaims)

        return TokenResponse(
            accessToken = accessToken,
            expiresIn = jwtProvider.getExpiredIn(),
            refreshToken = newRefreshToken,
            refreshTokenExpiresIn = jwtProvider.getRefreshExpiredIn(),
        )
    }

    /**
     * 1. 토큰 저장소에 없다면 없으면 유효하지 않은 토큰
     * 2. 해당 유저가 현재 사용하고 있는 토큰과 다른 토큰이 들어왔다면 중복된 로그인 감지
     */
    private fun validateToken(
        refreshToken: String,
        userId: ObjectId,
    ) {
        val storedToken =
            refreshTokenRepository.findByUserId(userId)?.token
                ?: throw UnAuthorizedException(ErrorType.INVALID_REFRESH_TOKEN)

        if (storedToken != refreshToken) {
            refreshTokenRepository.deleteByUserId(userId)
            throw UnAuthorizedException(ErrorType.DUPLICATE_USER_LOGIN)
        }
    }

    private fun generateAccessToken(userClaims: UserClaims): String {
        return jwtProvider.generateAccessToken(userClaims.id, userClaims.email)
    }

    private fun generateAndStoreRefreshToken(userClaims: UserClaims): String {
        val newRefreshToken = jwtProvider.generateRefreshToken(userClaims.id, userClaims.email)
        val refreshTokenExpiresIn = jwtProvider.getRefreshExpiredIn()

        refreshTokenRepository.save(RefreshToken.create(userClaims.id, newRefreshToken, refreshTokenExpiresIn))
        return newRefreshToken
    }
}