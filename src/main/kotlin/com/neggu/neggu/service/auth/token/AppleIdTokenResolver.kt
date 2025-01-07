package com.neggu.neggu.service.auth.token

import com.neggu.neggu.api.AppleOauthClient
import com.neggu.neggu.config.properties.AppleOauthProperties
import com.neggu.neggu.exception.ErrorType
import com.neggu.neggu.exception.UnAuthorizedException
import com.neggu.neggu.model.auth.OidcPublicKeys
import com.neggu.neggu.model.auth.OidcUser
import org.springframework.stereotype.Component

@Component
class AppleIdTokenResolver(
    private val appleOauthProperties: AppleOauthProperties,
    private val appleOauthClient: AppleOauthClient,
    private val idTokenProcessor: IdTokenProcessor,
) : OpenIdTokenResolver {
    override fun resolveIdToken(idToken: String): OidcUser {
        val oidcPublicKeys: OidcPublicKeys = appleOauthClient.getPublicKeys()
        try {
            return idTokenProcessor.process(
                idToken,
                oidcPublicKeys,
                appleOauthProperties.iss,
                appleOauthProperties.aud,
            )
        } catch (e: Exception) {
            throw UnAuthorizedException(ErrorType.InvalidIdToken)
        }
    }
}