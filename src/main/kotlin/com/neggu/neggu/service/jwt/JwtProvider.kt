package com.neggu.neggu.service.jwt

import com.neggu.neggu.config.properties.JwtProperties
import com.neggu.neggu.exception.ErrorType
import com.neggu.neggu.exception.UnAuthorizedException
import com.neggu.neggu.model.oauth.RegisterClaims
import com.neggu.neggu.model.oauth.UserClaims
import com.neggu.neggu.model.user.OauthProvider
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.bson.types.ObjectId
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties,
) {

    fun generateRegisterToken(
        nickname: String,
        provider: OauthProvider,
    ): String {
        return generateToken(nickname, provider.name, jwtProperties.access.expiry, jwtProperties.access.secret)
    }

    fun generateAccessToken(
        userId: ObjectId?,
        nickname: String,
    ): String {
        return generateToken(userId, nickname, jwtProperties.access.expiry, jwtProperties.access.secret)
    }

    fun generateRefreshToken(
        userId: ObjectId?,
        nickname: String,
    ): String {
        return generateToken(userId, nickname, jwtProperties.refresh.expiry, jwtProperties.refresh.secret)
    }

    fun resolveRegisterToken(token: String?): RegisterClaims {
        val secretKey: SecretKey = getSecretKey(jwtProperties.access.secret)
        val claims = getClaims(token, secretKey)
        return RegisterClaims(
            claims["nickname"] as String,
            claims["provider"] as String,
        )
    }

    fun resolveAccessToken(token: String?): UserClaims {
        return resolveToken(token, jwtProperties.access.secret)
    }

    fun resolveRefreshToken(token: String?): UserClaims {
        return resolveToken(token, jwtProperties.refresh.secret)
    }

    fun getRefreshTokenExpiration(token: String): Long {
        val secretKey: SecretKey = getSecretKey(jwtProperties.refresh.secret)
        val claims: Claims = getClaims(token, secretKey)
        return claims["exp"] as Long
    }

    fun getExpiredIn(): Long {
        val expiryInMillis: Long = jwtProperties.access.expiry
        return expiryInMillis / 1000
    }

    fun getRefreshExpiredIn(): Long {
        val expiryInMillis: Long = jwtProperties.refresh.expiry
        return expiryInMillis / 1000
    }

    private fun generateToken(
        nickname: String,
        provider: String,
        expiryTime: Long,
        secret: String,
    ): String {
        val now = Date()
        val expiry = Date(now.time + expiryTime)
        return Jwts.builder()
            .expiration(expiry)
            .claim("nickname", nickname)
            .claim("provider", provider)
            .signWith(getSecretKey(secret))
            .compact()
    }

    private fun generateToken(
        userId: ObjectId?,
        nickname: String,
        expiryTime: Long,
        secret: String,
    ): String {
        val now = Date()
        val expiry = Date(now.time + expiryTime)
        return Jwts.builder()
            .expiration(expiry)
            .claim("id", userId.toString())
            .claim("nickname", nickname)
            .signWith(getSecretKey(secret))
            .compact()
    }

    private fun resolveToken(
        token: String?,
        secret: String,
    ): UserClaims {
        val secretKey: SecretKey = getSecretKey(secret)
        val claims = getClaims(token, secretKey)
        return UserClaims(
            ObjectId(claims["id"] as String),
            claims["nickname"] as String,
        )
    }

    fun getClaims(
        token: String?,
        key: SecretKey,
    ): Claims {
        return try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token).payload
        } catch (e: ExpiredJwtException) {
            throw UnAuthorizedException(ErrorType.TOKEN_EXPIRED)
        } catch (e: Exception) {
            throw UnAuthorizedException(ErrorType.INVALID_JWT_TOKEN)
        }
    }

    private fun getSecretKey(secret: String): SecretKey {
        return Keys.hmacShaKeyFor(secret.toByteArray())
    }
}