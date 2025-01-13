package com.neggu.neggu.service.user

import com.neggu.neggu.dto.user.DuplicateNicknameResponse
import com.neggu.neggu.dto.user.TokenResponse
import com.neggu.neggu.dto.user.UserRegisterRequest
import com.neggu.neggu.exception.ErrorType
import com.neggu.neggu.exception.ServerException
import com.neggu.neggu.model.auth.OauthProvider
import com.neggu.neggu.model.auth.RefreshToken
import com.neggu.neggu.model.user.User
import com.neggu.neggu.repository.RefreshTokenRepository
import com.neggu.neggu.repository.UserRepository
import com.neggu.neggu.service.jwt.JwtProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserRegisterService(
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
) {

    @Transactional
    fun registerUser(
        email: String,
        provider: OauthProvider,
        userRegisterRequest: UserRegisterRequest,
    ): TokenResponse {
        if (checkDuplicateNickname(userRegisterRequest.nickname)) { throw ServerException(ErrorType.DuplicateNickanme) }
        val user = userRepository.save(
            User(
                email = email,
                profileImage = null,
                nickname = userRegisterRequest.nickname,
                gender = userRegisterRequest.gender,
                mood = userRegisterRequest.mood,
                age = userRegisterRequest.age,
                oauthProvider = provider,
            )
        )
        val refreshToken: String = jwtProvider.generateRefreshToken(user.id, user.email)
        val refreshTokenExpiresIn: Long = jwtProvider.getRefreshExpiredIn()

        refreshTokenRepository.save(RefreshToken.create(user.id!!, refreshToken, refreshTokenExpiresIn))
        return TokenResponse(
            accessToken = jwtProvider.generateAccessToken(user.id, user.email),
            expiresIn = jwtProvider.getExpiredIn(),
            refreshToken = refreshToken,
            refreshTokenExpiresIn = refreshTokenExpiresIn,
        )
    }

    fun checkNickname(nickname: String): DuplicateNicknameResponse {
        val isDuplicate = checkDuplicateNickname(nickname)
        return DuplicateNicknameResponse(isDuplicate)
    }

    private fun checkDuplicateNickname(nickname: String): Boolean {
        return userRepository.existsByNickname(nickname)
    }
}