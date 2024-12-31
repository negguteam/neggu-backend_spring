package com.neggu.neggu.service.oauth

import com.neggu.neggu.config.LoggerConfig.Companion.log
import com.neggu.neggu.exception.ErrorType
import com.neggu.neggu.exception.UnAuthorizedException
import com.neggu.neggu.model.oauth.OidcPublicKeys
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.security.PublicKey

@Component
class IdTokenValidator(
    private val publicKeyGenerator: PublicKeyGenerator,
) {

    fun verifySignature(
        idToken: String,
        header: Map<String, Any>,
        oidcPublicKeys: OidcPublicKeys,
    ): Claims {
        val publicKey: PublicKey = publicKeyGenerator.generatePublicKey(header, oidcPublicKeys)
        return try {
            Jwts.parser().verifyWith(publicKey).build().parseSignedClaims(idToken).payload
        } catch (e: Exception) {
            log.error { "Signature verification failed: ${e.message}" }
            throw e
        }
    }

    fun verifyPayload(
        payload: Map<String, Any>,
        iss: String,
        aud: String,
    ) {
        payload.apply {
            require(iss == this["iss"]) {
                log.error { "iss is $iss but iss in payload is ${this["iss"]}" }
                throw UnAuthorizedException(ErrorType.INVALID_ID_TOKEN)
            }
            require(aud == this["aud"]) {
                log.error { "aud is $aud but aud in payload is ${this["aud"]}" }
                throw UnAuthorizedException(ErrorType.INVALID_ID_TOKEN)
            }
            require((this["exp"] as Number).toLong() >= System.currentTimeMillis() / 1000) {
                log.error { "token is expired exp in payload is ${this["exp"]}" }
                throw UnAuthorizedException(ErrorType.INVALID_ID_TOKEN)
            }
        }
    }
}