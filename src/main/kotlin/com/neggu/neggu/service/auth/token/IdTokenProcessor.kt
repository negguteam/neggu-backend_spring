package com.neggu.neggu.service.auth.token

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.neggu.neggu.config.LoggerConfig.log
import com.neggu.neggu.config.LoggerConfig.nDebug
import com.neggu.neggu.model.auth.OauthProvider
import com.neggu.neggu.model.auth.OidcPublicKeys
import com.neggu.neggu.model.auth.OidcUser
import io.jsonwebtoken.Claims
import org.springframework.stereotype.Component
import java.util.*


@Component
class IdTokenProcessor(
    private val idTokenValidator: IdTokenValidator,
    private val objectMapper: ObjectMapper,
) {

    fun process(
        oauthProvider: OauthProvider,
        idToken: String,
        oidcPublicKeys: OidcPublicKeys,
        iss: String,
        aud: String,
    ): OidcUser {
        val splitToken = idToken.split(".")
        val header = parseHeader(splitToken[0])
        val payload = parsePayload(splitToken[1])
        idTokenValidator.verifyPayload(payload, iss, aud)
        val claims = idTokenValidator.verifySignature(idToken, header, oidcPublicKeys)
        return getUserClaims(oauthProvider, claims).also {
            log.nDebug("claims : $it")
        }
    }

    private fun parseHeader(encodedHeader: String): Map<String, Any> {
        val headerJson = base64Decode(encodedHeader)
        return objectMapper.readValue<Map<String, Any>>(headerJson)
    }

    private fun parsePayload(encodedPayload: String): Map<String, Any> {
        val payloadJson = base64Decode(encodedPayload)
        return objectMapper.readValue<Map<String, Any>>(payloadJson)
    }

    private fun getUserClaims(oauthProvider: OauthProvider, claims: Claims): OidcUser {
        val email = claims["email"].toString()
        val profileImage: String? = when (oauthProvider) {
            OauthProvider.GOOGLE -> claims["picture"].toString()
            OauthProvider.APPLE -> null
            OauthProvider.KAKAO -> claims["picture"]?.toString()
        }
        return OidcUser(email, profileImage).also {
            log.nDebug("OidcUser : $it")
        }
    }

    private fun base64Decode(encodedString: String): String {
        return String(Base64.getUrlDecoder().decode(encodedString))
    }
}