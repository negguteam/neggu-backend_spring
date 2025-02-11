package com.neggu.neggu.service.jwt

import com.neggu.neggu.config.properties.JwtProperties
import com.neggu.neggu.exception.ErrorType
import com.neggu.neggu.exception.UnAuthorizedException
import com.neggu.neggu.model.auth.RegisterClaims
import com.neggu.neggu.model.auth.UserClaims
import com.neggu.neggu.model.auth.OauthProvider
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
        email: String,
        profileImage: String?,
        provider: OauthProvider,
    ): String {
        return generateToken(email, profileImage, provider.name, jwtProperties.access.expiry, jwtProperties.access.secret)
    }

    fun generateAccessToken(
        userId: ObjectId?,
        email: String,
    ): String {
        return generateToken(userId, email, jwtProperties.access.expiry, jwtProperties.access.secret)
    }

    fun generateRefreshToken(
        userId: ObjectId?,
        email: String,
    ): String {
        return generateToken(userId, email, jwtProperties.refresh.expiry, jwtProperties.refresh.secret)
    }

    fun resolveRegisterToken(token: String?): RegisterClaims {
        val secretKey: SecretKey = getSecretKey(jwtProperties.access.secret)
        val claims = getClaims(token, secretKey)
        return RegisterClaims(
            email = claims["email"] as String,
            profileImage = claims["profileImage"] as String?,
            provider = claims["provider"] as String,
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

    fun getExpiredIn(currentTime: Long = System.currentTimeMillis()): Long {
        return jwtProperties.access.expiry + currentTime
    }

    fun getRefreshExpiredIn(currentTime: Long = System.currentTimeMillis()): Long {
        return jwtProperties.refresh.expiry + currentTime
    }

    private fun generateToken(
        email: String,
        profileImage: String?,
        provider: String,
        expiryTime: Long,
        secret: String,
    ): String {
        val now = Date()
        val expiry = Date(now.time + expiryTime)
        return Jwts.builder()
            .expiration(expiry)
            .claim("email", email)
            .claim("provider", provider)
            .claim("profileImage", profileImage)
            .signWith(getSecretKey(secret))
            .compact()
    }

    private fun generateToken(
        userId: ObjectId?,
        email: String,
        expiryTime: Long,
        secret: String,
    ): String {
        val now = Date()
        val expiry = Date(now.time + expiryTime)
        return Jwts.builder()
            .expiration(expiry)
            .claim("id", userId.toString())
            .claim("email", email)
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
            claims["email"] as String,
        )
    }

    fun getClaims(
        token: String?,
        key: SecretKey,
    ): Claims {
        return try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token).payload
        } catch (e: ExpiredJwtException) {
            throw UnAuthorizedException(ErrorType.TokenExpired)
        } catch (e: Exception) {
            throw UnAuthorizedException(ErrorType.InvalidJwtToken)
        }
    }

    private fun getSecretKey(secret: String): SecretKey {
        return Keys.hmacShaKeyFor(secret.toByteArray())
    }
}